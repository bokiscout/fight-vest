using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;
using Microsoft.EntityFrameworkCore;
using web.Models;

namespace web.Controllers
{
    public class AdminController : Controller
    {
        private ApplicationDbContext db;
        private readonly IHostingEnvironment _hostingEnvironment;
        private readonly UserManager<ApplicationUser> userManager;
        
        public AdminController(ApplicationDbContext dbContext, IHostingEnvironment _hostingEnvironment, 
        UserManager<ApplicationUser> userManager)
        {
            db = dbContext;
            this._hostingEnvironment = _hostingEnvironment;
            this.userManager = userManager;
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
            AdminFightsViewModel vm = new AdminFightsViewModel();
            vm.Fights = db.Fights.Include(f => f.FightFighters).ThenInclude(f => f.Fight).Include(f => f.FightType).Include(f => f.Rounds).ToList();
            
            return View(vm);
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
        public async Task<IActionResult> PostAddFighter(AddFighterViewModel vm)
        {
            ViewData["Title"] = "Додади Борец";

            Fighter fighter = new Fighter
            {
                FirstName = vm.Fighter.FirstName,
                LastName = vm.Fighter.LastName,
                BirthDate = vm.Fighter.BirthDate,
                FighterCategoryID = vm.Fighter.FighterCategoryID,
                Country = vm.Fighter.Country,
                City = vm.Fighter.City
            };

           if(vm.File != null && vm.File.Length > 0)
           {
               string directory = _hostingEnvironment.WebRootPath + "\\uploads";               
               string extension = GetImageExtension(vm.File.ContentType);
               string fileName  = string.Format("{0}.{1}", Guid.NewGuid().ToString(), extension);
               string filePath = string.Format("{0}\\{1}", directory, fileName);
               
               if(!Directory.Exists(directory))
               {
                    Directory.CreateDirectory(directory);
               }

               using(var stream = new FileStream(filePath, FileMode.Create))
               {
                   await vm.File.CopyToAsync(stream);
               }
               

               fighter.Avatar = fileName;
           }

           db.Fighters.Add(fighter);
           db.SaveChanges();
           
            return RedirectToAction("Fighters");
        }

        private string GetImageExtension(string contentType)
        {
            if(contentType == "image/jpeg"){
                return "jpg";
            }
            if(contentType == "image/png"){
                return "png";
            }
            if(contentType == "image/gif"){
                return "gif";
            }

            return null;
        }

        public IActionResult EditFighter(int id)
        {
            ViewData["Title"] = "Измени податоци за борец";
            
            Fighter fighter = db.Fighters.Include(f => f.FighterCategory).FirstOrDefault(f => f.ID == id);
            IEnumerable<FighterCategory> categories = db.FighterCategories.ToList();
            
            AddFighterViewModel vm = new AddFighterViewModel();            
            vm.Categories = categories.Select(c => new SelectListItem { Value = c.ID.ToString(), Text = c.Name});
            vm.Fighter = fighter;

            return View(vm);
        }

         [HttpPost]
        public async Task<IActionResult> PostEditFighter(AddFighterViewModel vm)
        {
            ViewData["Title"] = "Додади Борец";

            Fighter fighter = db.Fighters.Find(vm.Fighter.ID);

            fighter.FirstName = vm.Fighter.FirstName;
            fighter.LastName = vm.Fighter.LastName;
            fighter.BirthDate = vm.Fighter.BirthDate;
            fighter.FighterCategoryID = vm.Fighter.FighterCategoryID;
            fighter.Country = vm.Fighter.Country;
            fighter.City = vm.Fighter.City;

            if(vm.File != null && vm.File.Length > 0)
            {
               string directory = _hostingEnvironment.WebRootPath + "\\uploads";               
               string extension = GetImageExtension(vm.File.ContentType);
               string fileName  = string.Format("{0}.{1}", Guid.NewGuid().ToString(), extension);
               string filePath = string.Format("{0}\\{1}", directory, fileName);
               
               if(!Directory.Exists(directory))
               {
                    Directory.CreateDirectory(directory);
               }

               using(var stream = new FileStream(filePath, FileMode.Create))
               {
                   await vm.File.CopyToAsync(stream);
               }
               

               fighter.Avatar = fileName;
            }
           
            db.Fighters.Update(fighter);
            db.SaveChanges();

            return RedirectToAction("Fighters");
        }

        public IActionResult Remove(int id)
        {
            Fighter fighter = db.Fighters.Find(id);
            db.Fighters.Remove(fighter);
            db.SaveChanges();

            return RedirectToAction("Fighters");
        }

        public IActionResult AddFight()
        {
            ViewData["Title"] = "Додади Борба";
            
            AddFightViewModel vm = new AddFightViewModel();
            vm.Fighters = db.Fighters.Select(f => new SelectListItem { Value = f.ID.ToString(), Text = f.FullName});
            vm.FightTypes = db.FightTypes.Select(f => new SelectListItem { Value = f.ID.ToString(), Text = f.Name});
            vm.Fight = new Fight();

            return View(vm);
        }

        public IActionResult PostAddFight(AddFightViewModel vm)
        {
            Fight fight = new Fight();
            var forms = Request;
            fight.Address = vm.Fight.Address;
            fight.City = vm.Fight.City;
            fight.Country = vm.Fight.Country;
            fight.EndTime = vm.Fight.EndTime;
            fight.StartTime = vm.Fight.StartTime;
            fight.FightTypeID = vm.Fight.FightTypeID;
            fight.UserID = userManager.GetUserId(User);
            fight.Rounds = new List<Round>();

            fight.FightFighters = new List<FightFighters>
            {
                new FightFighters
                {
                    Fighter = db.Fighters.Find(vm.FirstFighter)
                },
                new FightFighters
                {
                    Fighter = db.Fighters.Find(vm.SecondFighter)
                }
            };

            for(int i=0; i<vm.NumberOfRounds; i++)
            {
                Round round = new Round();
                fight.Rounds.Add(round);
            }

            db.Fights.Add(fight);
            db.SaveChanges();
            
            return RedirectToAction("Fights");
        }

        public IActionResult EditFight(int id)
        {
            ViewData["Title"] = "Измени податоци за борец";
            
            AddFightViewModel vm = new AddFightViewModel();
            vm.Fight = db.Fights.Include(f => f.Rounds).FirstOrDefault(f => f.ID == id);

            vm.Fighters = db.Fighters.Select(f => new SelectListItem { Value = f.ID.ToString(), Text = f.FullName});
            vm.FightTypes = db.FightTypes.Select(f => new SelectListItem { Value = f.ID.ToString(), Text = f.Name});
            vm.FirstFighter = db.FightFighters.Where(f => f.FightID == id).OrderBy(f => f.FighterID).Select(f => f.FighterID).FirstOrDefault();
            vm.SecondFighter = db.FightFighters.Where(f => f.FightID == id).OrderByDescending(f => f.FighterID).Select(f => f.FighterID).FirstOrDefault();
            vm.NumberOfRounds = vm.Fight.Rounds.Count;
            
            return View(vm);
        }

        //[HttpPost]
       // public IActionResult PostEditFight(AddFightViewModel vm)
       // {
            Fight fight = db.figh
        //}
    }
}
