using System;
using System.Collections.Generic;
using Microsoft.AspNetCore.Mvc.Rendering;
using web.Models;

namespace web
{
    public class AddFighterViewModel
    {
        public Fighter Fighter { get; set; }
        public IEnumerable<SelectListItem> Categories { get; set; }
    }
}