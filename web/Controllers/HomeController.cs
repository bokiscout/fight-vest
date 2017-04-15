﻿using Microsoft.AspNetCore.Mvc;

namespace web.Controllers
{
    public class HomeController : Controller
    {
        public IActionResult Index()
        {
            ViewData["Title"] = "Home";
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
