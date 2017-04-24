using System.Collections.Generic;
using Microsoft.AspNetCore.Mvc.Rendering;
using web.Models;

namespace web
{
    public class AddFightViewModel
    {
        public Fight Fight { get; set; }
        public int FirstFighter { get; set; }
        public int SecondFighter { get; set; }
        public IEnumerable<SelectListItem> Fighters { get; set; }
        public IEnumerable<SelectListItem> FightTypes { get; set; }
    }
}