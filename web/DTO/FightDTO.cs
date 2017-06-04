using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace web.DTO
{
    public class FightDTO
    {
        public int ID { get; set; }
        public DateTime StartTime { get; set; }
        public DateTime EndTime { get; set; }
        public string Country { get; set; }
        public string City { get; set; }
        public string Address { get; set; }
        public string Description { get; set; }

        public DateTime? StartedAt { get; set; }
        public DateTime? EndedAt { get; set; }
        public int FightTypeID { get; set; }
        public virtual FightTypeDTO FightType { get; set; }

        public ICollection<FightFightersDTO> FightFighters { get; set; }
        public ICollection<RoundDTO> Rounds { get; set; }
    }
}
