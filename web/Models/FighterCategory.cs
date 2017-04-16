using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;

namespace web.Models
{
    public class FighterCategory
    {
        public int ID { get; set; }

        [Required]
        public string Name { get; set; }

        public virtual ICollection<Fighter> Fighers { get; set; }
    }
}