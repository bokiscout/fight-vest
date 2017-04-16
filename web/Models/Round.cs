using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace web.Models
{
    public class Round
    {
        public int ID { get; set; }
        public DateTime StartTime { get; set; }
        public DateTime EndTime { get; set; }
       
        [Required]
        [ForeignKey("Fight")]
        public int FightID { get; set; }

        public Fight Fight { get; set; }
    }
}