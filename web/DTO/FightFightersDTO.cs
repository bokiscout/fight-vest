using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace web.DTO
{
    public class FightFightersDTO
    {
        public int ID { get; set; }
        public int FightID { get; set; }
        public int FighterID { get; set; }
        public FighterDTO Fighter { get; set; }
    }
}
