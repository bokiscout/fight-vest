using System;
using web.Models;

namespace web
{
    public class PublicFighterViewModel
    {
        public int ID { get; set; }
        public Fighter Fighter { get; set; }

        public int TotalFights { get; set; }
        public int WonFights { get; set; }
        public int LostFights { get; set; }

    }
}