using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace web.Models
{
    public class Fighter
    {
        public int ID { get; set; }
        public string FirstName { get; set; }
        public string LastName { get; set; }

        public DateTime BirthDate { get; set; }
        public string FullName
        {
            get
            {
                return string.Format("{0} {1}", FirstName, LastName);
            }
        }

        [Required]
        [ForeignKey("FighterCategory")]
        public int FighterCategoryID { get; set; }
        public FighterCategory FighterCategory { get; set; }

        public virtual ICollection<FightFighters> FightFighters { get; set; }
    }
}