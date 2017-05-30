using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace web.Models
{
    public class Fight
    {
        public int ID { get; set; }
        public DateTime StartTime { get; set; }
        public DateTime EndTime { get; set; }
        public string Country { get; set; }
        public string City { get; set; }
        public string Address { get; set; }
        public string Description { get; set; }

        [Required]
        [ForeignKey("User")]
        public string UserID { get; set; }
        public ApplicationUser User { get; set; }

        [Required]
        [ForeignKey("FightType")]
        public int FightTypeID { get; set; }
        public virtual FightType FightType { get; set;}

        public virtual ICollection<FightFighters> FightFighters { get; set; }

        public virtual ICollection<Round> Rounds { get; set; }
    }
}