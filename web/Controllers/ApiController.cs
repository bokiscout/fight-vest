using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using web.Models;

namespace web.Controllers
{
    [Route("api")]
    public class ApiController : Controller
    {
        private ApplicationDbContext db;

        public ApiController(ApplicationDbContext db)
        {
            this.db = db;
        }

        [HttpGet]
        [Route("Fights")]
        public IActionResult GetFights()
        {
            IEnumerable<Fight> fights = db.Fights.Include(f => f.FightFighters).ThenInclude(ff => ff.Fighter).ToList();

            return Ok(fights);
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

            return Ok(fight);
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

            return Ok(fight);
        }

        [HttpPost]
        [Route("Fight/Round/Start/{id:int}")]
        public IActionResult StartNextRound(int id)
        {
            Fight fight = db.Fights.Include(f => f.Rounds).FirstOrDefault();

            if (fight.StartedAt == null)
            {
                return BadRequest("The fight has not been started yet");
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

            return Ok(round);
        }

        [HttpPost]
        [Route("Fight/Round/End/{id:int}")]
        public IActionResult EndLastRound(int id)
        {
            Fight fight = db.Fights.Include(f => f.Rounds).FirstOrDefault();

            if (fight.StartedAt == null)
            {
                return BadRequest("The fight has not been started yet");
            }

            Round lastRound = fight.Rounds.FirstOrDefault(r => r.StartTime != null && r.EndTime == null);

            if (lastRound == null)
            {
                return BadRequest("There is not active round at the moment");
            }

            lastRound.EndTime = DateTime.UtcNow;
            db.Round.Update(lastRound);
            db.SaveChanges();

            return Ok(lastRound);
        }

        [HttpPost]
        [Route("Fight/{fightId:int}/Hit/{fighterId:int}")]
        public IActionResult Hit(int fightId, int fighterId)
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

            Hit hit = new Hit()
            {
                FighterID = fighterId,
                Timestamp = DateTime.UtcNow
            };

            lastRound.Hits.Add(hit);


            db.Fights.Update(fight);
            db.SaveChanges();

            return Ok(hit);
        }

    }
}
