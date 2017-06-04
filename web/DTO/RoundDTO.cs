using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace web.DTO
{
    public class RoundDTO
    {
        public int ID { get; set; }
        public DateTime StartTime { get; set; }
        public DateTime? EndTime { get; set; }
        public int FightID { get; set; }
        public ICollection<HitDTO> Hits { get; set; }
    }
}
