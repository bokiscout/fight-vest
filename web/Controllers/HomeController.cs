using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using System;
using System.Linq;
using web.Models;

namespace web.Controllers
{
    public class HomeController : Controller
    {
        private ApplicationDbContext db;

        public HomeController(ApplicationDbContext db)
        {
            this.db = db;
        }

        public IActionResult Index()
        {
            ViewData["Title"] = "Home";
            return View();
        }

        public IActionResult Fighters()
        {
            ViewData["Title"] = "Борци";

            FightersSearchResultViewModel result = new FightersSearchResultViewModel();
            result.Fighters = db.Fighters.Include(f => f.FighterCategory).Select(f => new PublicFighterViewModel
            {
                Fighter = f,
                ID = f.ID,
                LostFights = 2,
                TotalFights = 10,
                WonFights = 8
            }).ToList();

            return View(result);
        }

        public IActionResult Fighter(int id)
        {
            PublicFighterViewModel fighter = db.Fighters.Include(f => f.FighterCategory).Where(f => f.ID == id)
                .Select(f => new PublicFighterViewModel
            {
                ID = f.ID,
                Fighter = f,
                LostFights = 2,
                TotalFights = 10,
                WonFights = 8
            }).FirstOrDefault();

            FighterProfileViewModel result = new FighterProfileViewModel();

            result.Fighter = fighter;
            result.NextFights = db.Fights.Include(f => f.FightFighters).ThenInclude(ff => ff.Fighter).Include(f => f.FightType)
                .Where(f =>  f.StartTime >= DateTime.Now);

            result.PreviousFights = db.Fights.Include(f => f.FightFighters).Include(f => f.FightType).Include(f => f.Rounds)
                .Where(f => f.StartTime < DateTime.Now);

            return View(result);
        }

        public IActionResult Fights()
        {
            ViewData["Title"] = "Борби";
            FightsSearchResultViewModel vm = new FightsSearchResultViewModel();
            vm.Fights = db.Fights.Include(f => f.FightFighters).ThenInclude(f => f.Fighter).Include(f => f.FightType).Include(f => f.Rounds).ToList();

            return View(vm);
        }
    }
}
