using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace web.Models
{
    public class Hit
    {
        public int ID { get; set; }
        public DateTime Timestamp { get; set; }    

        [Required]
        [ForeignKey("Round")]
        public int RoundID { get; set; }  
        public Round Round { get; set; }  

        [Required]
        [ForeignKey("Fighter")]
        public int FighterID { get; set; }    
        public Fighter Fighter { get; set; }

        public string Type { get; set; }
    }
}