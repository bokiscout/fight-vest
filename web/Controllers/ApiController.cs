using AutoMapper;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.SignalR.Infrastructure;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using web.DTO;
using web.Hubs;
using web.Models;

namespace web.Controllers
{
    [Route("api")]
    public class ApiController : Controller
    {
        private ApplicationDbContext db;
        private IConnectionManager connectionManager;
        private IMapper mapper;

        public ApiController(ApplicationDbContext db, IConnectionManager connectionManager, IMapper mapper)
        {
            this.db = db;
            this.connectionManager = connectionManager;
            this.mapper = mapper;
        }

        [HttpGet]
        [Route("Fights")]
        public IActionResult GetFights()
        {
            IEnumerable<Fight> fights = db.Fights.Include(f => f.FightFighters).ThenInclude(ff => ff.Fighter).ToList();
            IEnumerable<FightDTO> fightsDto = mapper.Map<IEnumerable<FightDTO>>(fights);
            
            return Ok(fightsDto);
        }

        [HttpGet]
        [Route("Fights/{id:int}")]
        public IActionResult GetFight(int id)
        {
            Fight fight = GetActiveFight(id);
            FightDTO fightDto = mapper.Map<FightDTO>(fight);
            return Ok(fightDto);
        }

        private Fight GetActiveFight(int id)
        {
            Fight fight = db.Fights.Include(f => f.FightFighters).ThenInclude(ff => ff.Fighter)
                .Include(f => f.Rounds).ThenInclude(r => r.Hits).Where(f => f.ID == id).FirstOrDefault();

            return fight;
        }

        [HttpPost]
        [Route("Fight/Start/{id:int}")]
        public IActionResult StartFight(int id)
        {
            Fight fight = db.Fights.Find(id);
            
            if(fight.StartedAt != null)
            {
                return BadRequest("The fight has already been started");
            }

            fight.StartedAt = DateTime.UtcNow;
            db.Fights.Update(fight);
            db.SaveChanges();

            fight = GetActiveFight(id);
            FightDTO fightDto = mapper.Map<FightDTO>(fight);

            connectionManager.GetHubContext<FightHub>().Clients.Group(GetGroupKey(id)).OnFightStarted(fightDto);

            return Ok(fightDto);
        }

        [HttpPost]
        [Route("Fight/End/{id:int}")]
        public IActionResult EndFight(int id)
        {
            Fight fight = db.Fights.Find(id);

            if (fight.StartedAt == null)
            {
                return BadRequest("The fight has not been started yet");
            }
            if(fight.EndedAt != null)
            {
                return BadRequest("The fight has already ended");
            }

            fight.EndedAt = DateTime.UtcNow;
            db.Fights.Update(fight);
            db.SaveChanges();

            fight = GetActiveFight(id);
            FightDTO fightDto = mapper.Map<FightDTO>(fight);

            connectionManager.GetHubContext<FightHub>().Clients.Group(GetGroupKey(id)).OnFightEnded(fightDto);

            return Ok(fightDto);
        }

        [HttpPost]
        [Route("Fight/Round/Start/{id:int}")]
        public IActionResult StartNextRound(int id)
        {
            Fight fight = db.Fights.Include(f => f.Rounds).Where(f => f.ID == id).FirstOrDefault();

            if (fight.StartedAt == null)
            {
                return BadRequest("The fight has not been started yet");
            }
            if(fight.EndedAt != null)
            {
                return BadRequest("The fight has already ended");
            }

            Round activeRound = fight.Rounds.FirstOrDefault(r => r.StartTime != null && r.EndTime == null);

            if(activeRound != null)
            {
                return BadRequest("The last round is not finished yet");
            }

            Round round = new Round()
            {
                StartTime = DateTime.UtcNow,
                Hits = new List<Hit>()
            };

            fight.Rounds.Add(round);

            db.Fights.Update(fight);
            db.SaveChanges();

            fight = GetActiveFight(id);
            FightDTO fightDto = mapper.Map<FightDTO>(fight);

            connectionManager.GetHubContext<FightHub>().Clients.Group(GetGroupKey(id)).OnRoundStarted(fightDto);

            return Ok(round);
        }

        [HttpPost]
        [Route("Fight/Round/End/{id:int}")]
        public IActionResult EndLastRound(int id)
        {
            Fight fight = db.Fights.Include(f => f.Rounds).Where(f => f.ID == id).FirstOrDefault();

            if (fight.StartedAt == null)
            {
                return BadRequest("The fight has not been started yet");
            }
            if(fight.EndedAt != null)
            {
                return BadRequest("The fight has already ended");
            }

            Round lastRound = fight.Rounds.FirstOrDefault(r => r.StartTime != null && r.EndTime == null);

            if (lastRound == null)
            {
                return BadRequest("There is not active round at the moment");
            }

            lastRound.EndTime = DateTime.UtcNow;
            db.Round.Update(lastRound);
            db.SaveChanges();

            fight = GetActiveFight(id);
            FightDTO fightDto = mapper.Map<FightDTO>(fight);

            connectionManager.GetHubContext<FightHub>().Clients.Group(GetGroupKey(id)).OnRoundEnded(fightDto);

            return Ok(lastRound);
        }

        [HttpPost]
        [Route("Fight/{fightId:int}/Hit/{fighterId:int}/{type}")]
        public IActionResult Hit(int fightId, int fighterId, string type)
        {
            Fight fight = db.Fights.Include(f => f.Rounds).ThenInclude(r => r.Hits).FirstOrDefault(f => f.ID == fightId);

            if (fight.StartedAt == null)
            {
                return BadRequest("The fight has not been started yet");
            }

            Round lastRound = fight.Rounds.FirstOrDefault(r => r.StartTime != null && r.EndTime == null);

            if (lastRound == null)
            {
                return BadRequest("There is no active round at the moment");
            }

            if(type != "strong" && type != "weak")
            {
                type = "weak";
            }

            Hit hit = new Hit()
            {
                FighterID = fighterId,
                Timestamp = DateTime.UtcNow,
                Type = type
            };

            lastRound.Hits.Add(hit);
            db.Fights.Update(fight);
            db.SaveChanges();

            HitDTO hitDto = mapper.Map<HitDTO>(hit);

            connectionManager.GetHubContext<FightHub>().Clients.Group(GetGroupKey(fightId)).OnHit(fight.ID, fighterId, hitDto);

            return Ok(hit);
        }

        private string GetGroupKey(int id)
        {
            return FightHub.GetFightKey(id);
        }
    }
}
