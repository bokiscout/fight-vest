using System;
using System.Collections.Generic;
using Microsoft.AspNetCore.Mvc.Rendering;
using web.Models;

namespace web
{
    public class AddFighterViewModel
    {
        public string FirstName { get; set; }
        public string LastName { get; set; }
        public DateTime BirthDate { get; set; }
        public string CategoryID { get; set; }
        public IEnumerable<SelectListItem> Categories { get; set; }
    }
}