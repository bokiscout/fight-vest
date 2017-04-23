using System.Collections.Generic;
using System.Linq;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;
using web.Models;

namespace web.Controllers
{
    public class AdminController : Controller
    {
        private ApplicationDbContext db;
        public AdminController(ApplicationDbContext dbContext)
        {
            db = dbContext;
        }

        public IActionResult Index()
        {
            ViewData["Title"] = "Admin";
            return View();
        }

        public IActionResult Fighters()
        {
            ViewData["Title"] = "Борци";

            AdminFightersViewModel vm = new AdminFightersViewModel();
            vm.Fighters = db.Fighters.Include(f => f.FighterCategory).ToList();

            return View(vm);
        }

        public IActionResult Fights()
        {
            ViewData["Title"] = "Борби";
            
            return View();
        }

        public IActionResult AddFighter()
        {
            ViewData["Title"] = "Додади Борец";

            IEnumerable<FighterCategory> categories = db.FighterCategories.ToList();
            
            AddFighterViewModel vm = new AddFighterViewModel();
            vm.Categories = categories.Select(c => new SelectListItem { Value = c.ID.ToString(), Text = c.Name});

            return View(vm);
        }

        [HttpPost]
        public IActionResult PostAddFighter(AddFighterViewModel vm)
        {
            ViewData["Title"] = "Додади Борец";

            Fighter fighter = new Fighter
            {
                FirstName = vm.FirstName,
                LastName = vm.LastName,
                BirthDate = vm.BirthDate,
                FighterCategoryID = int.Parse(vm.CategoryID)
            };
           
           db.Fighters.Add(fighter);
           db.SaveChanges();


            return RedirectToAction("Fighters");
        }

        public IActionResult AddFight()
        {
            ViewData["Title"] = "Додади Борба";
            return View();
        }
    }
}
