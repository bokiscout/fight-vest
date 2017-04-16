using Microsoft.AspNetCore.Identity.EntityFrameworkCore;
using System;
using System.Collections.Generic;

namespace web.Models
{
    // Add profile data for application users by adding properties to the ApplicationUser class
    public class ApplicationUser : IdentityUser
    {
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

        public virtual ICollection<Fight> Fights { get; set; }
    }
}
