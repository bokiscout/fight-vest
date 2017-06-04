using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace web.DTO
{
    public class HitDTO
    {
        public int ID { get; set; }
        public DateTime Timestamp { get; set; }
        public int FighterID { get; set; }
        public FighterDTO Fighter { get; set; }
    }
}
