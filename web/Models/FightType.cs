using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace web.Models
{
    public class FightType
    {
        [Key]
        public int ID { get; set; }

        [Required]
        public string Name { get; set; }

        public virtual ICollection<Fight> Fights { get; set; }
    }
}