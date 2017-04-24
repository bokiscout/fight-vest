using System.Collections.Generic;
using web.Models;

namespace web
{
    public class AdminFightsViewModel
    {
        public IEnumerable<Fight> Fights { get; set; }
    }
}