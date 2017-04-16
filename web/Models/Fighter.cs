using System;
using System.Collections.Generic;

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

        public virtual ICollection<FightFighters> FightFighters { get; set; }
    }
}