using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using web.Models;

namespace web
{
    public class FighterProfileViewModel
    {
        public PublicFighterViewModel Fighter { get; set; }
        public IEnumerable<Fight> NextFights { get; set; }
        public IEnumerable<Fight> PreviousFights { get; set; }
    }
}
