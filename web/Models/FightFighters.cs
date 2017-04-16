using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace web.Models
{
    public class FightFighters
    {
        public int ID { get; set; }

        [Required]
        [ForeignKey("Fight")]
        public int FightID { get; set; }
        public Fight Fight { get; set; }

        [Required]
        [ForeignKey("Fighter")]
        public int FighterID { get; set; }
        public Fighter Fighter { get; set; } 
    }
}