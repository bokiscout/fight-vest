using Microsoft.AspNetCore.Mvc;

namespace web.Controllers
{
    public class AdminController : Controller
    {
        public IActionResult Index()
        {
            ViewData["Title"] = "Admin";
            return View();
        }

        public IActionResult Fighters()
        {
            ViewData["Title"] = "Борци";
            return View();
        }

        public IActionResult Fights()
        {
            ViewData["Title"] = "Борби";
            
            return View();
        }
    }
}
