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
        public string Avatar { get; set; }
        public string Country { get; set; }
        public string City { get; set; }

        public DateTime BirthDate { get; set; }

        [NotMapped]
        public string FullName
        {
            get
            {
                return string.Format("{0} {1}", FirstName, LastName);
            }
        }

        [NotMapped]        
        public string AvatarUrl
        {
            get
            {
                return string.Format("/uploads/{0}", Avatar);
            }
        }

        [Required]
        [ForeignKey("FighterCategory")]
        public int FighterCategoryID { get; set; }
        public FighterCategory FighterCategory { get; set; }

        public virtual ICollection<FightFighters> FightFighters { get; set; }

        public virtual ICollection<Hit> Hits { get; set; }
    }
}