using System;
using System.Collections.Generic;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc.Rendering;
using web.Models;

namespace web
{
    public class AddFighterViewModel
    {
        public Fighter Fighter { get; set; }
        public IEnumerable<SelectListItem> Categories { get; set; }
        public IFormFile File { get; set; }
    }
}