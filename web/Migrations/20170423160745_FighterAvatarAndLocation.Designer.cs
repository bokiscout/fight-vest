using System;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Infrastructure;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;
using web;

namespace web.Migrations
{
    [DbContext(typeof(ApplicationDbContext))]
    [Migration("20170423160745_FighterAvatarAndLocation")]
    partial class FighterAvatarAndLocation
    {
        protected override void BuildTargetModel(ModelBuilder modelBuilder)
        {
            modelBuilder
                .HasAnnotation("ProductVersion", "1.1.1");

            modelBuilder.Entity("Microsoft.AspNetCore.Identity.EntityFrameworkCore.IdentityRole", b =>
                {
                    b.Property<string>("Id")
                        .ValueGeneratedOnAdd();

                    b.Property<string>("ConcurrencyStamp")
                        .IsConcurrencyToken();

                    b.Property<string>("Name")
                        .HasMaxLength(256);

                    b.Property<string>("NormalizedName")
                        .HasMaxLength(256);

                    b.HasKey("Id");

                    b.HasIndex("NormalizedName")
                        .IsUnique()
                        .HasName("RoleNameIndex");

                    b.ToTable("AspNetRoles");
                });

            modelBuilder.Entity("Microsoft.AspNetCore.Identity.EntityFrameworkCore.IdentityRoleClaim<string>", b =>
                {
                    b.Property<int>("Id")
                        .ValueGeneratedOnAdd();

                    b.Property<string>("ClaimType");

                    b.Property<string>("ClaimValue");

                    b.Property<string>("RoleId")
                        .IsRequired();

                    b.HasKey("Id");

                    b.HasIndex("RoleId");

                    b.ToTable("AspNetRoleClaims");
                });

            modelBuilder.Entity("Microsoft.AspNetCore.Identity.EntityFrameworkCore.IdentityUserClaim<string>", b =>
                {
                    b.Property<int>("Id")
                        .ValueGeneratedOnAdd();

                    b.Property<string>("ClaimType");

                    b.Property<string>("ClaimValue");

                    b.Property<string>("UserId")
                        .IsRequired();

                    b.HasKey("Id");

                    b.HasIndex("UserId");

                    b.ToTable("AspNetUserClaims");
                });

            modelBuilder.Entity("Microsoft.AspNetCore.Identity.EntityFrameworkCore.IdentityUserLogin<string>", b =>
                {
                    b.Property<string>("LoginProvider");

                    b.Property<string>("ProviderKey");

                    b.Property<string>("ProviderDisplayName");

                    b.Property<string>("UserId")
                        .IsRequired();

                    b.HasKey("LoginProvider", "ProviderKey");

                    b.HasIndex("UserId");

                    b.ToTable("AspNetUserLogins");
                });

            modelBuilder.Entity("Microsoft.AspNetCore.Identity.EntityFrameworkCore.IdentityUserRole<string>", b =>
                {
                    b.Property<string>("UserId");

                    b.Property<string>("RoleId");

                    b.HasKey("UserId", "RoleId");

                    b.HasIndex("RoleId");

                    b.ToTable("AspNetUserRoles");
                });

            modelBuilder.Entity("Microsoft.AspNetCore.Identity.EntityFrameworkCore.IdentityUserToken<string>", b =>
                {
                    b.Property<string>("UserId");

                    b.Property<string>("LoginProvider");

                    b.Property<string>("Name");

                    b.Property<string>("Value");

                    b.HasKey("UserId", "LoginProvider", "Name");

                    b.ToTable("AspNetUserTokens");
                });

            modelBuilder.Entity("web.Models.ApplicationUser", b =>
                {
                    b.Property<string>("Id")
                        .ValueGeneratedOnAdd();

                    b.Property<int>("AccessFailedCount");

                    b.Property<DateTime>("BirthDate");

                    b.Property<string>("ConcurrencyStamp")
                        .IsConcurrencyToken();

                    b.Property<string>("Email")
                        .HasMaxLength(256);

                    b.Property<bool>("EmailConfirmed");

                    b.Property<string>("FirstName");

                    b.Property<string>("LastName");

                    b.Property<bool>("LockoutEnabled");

                    b.Property<DateTimeOffset?>("LockoutEnd");

                    b.Property<string>("NormalizedEmail")
                        .HasMaxLength(256);

                    b.Property<string>("NormalizedUserName")
                        .HasMaxLength(256);

                    b.Property<string>("PasswordHash");

                    b.Property<string>("PhoneNumber");

                    b.Property<bool>("PhoneNumberConfirmed");

                    b.Property<string>("SecurityStamp");

                    b.Property<bool>("TwoFactorEnabled");

                    b.Property<string>("UserName")
                        .HasMaxLength(256);

                    b.HasKey("Id");

                    b.HasIndex("NormalizedEmail")
                        .HasName("EmailIndex");

                    b.HasIndex("NormalizedUserName")
                        .IsUnique()
                        .HasName("UserNameIndex");

                    b.ToTable("AspNetUsers");
                });

            modelBuilder.Entity("web.Models.Fight", b =>
                {
                    b.Property<int>("ID")
                        .ValueGeneratedOnAdd();

                    b.Property<DateTime>("EndTime");

                    b.Property<int>("FightTypeID");

                    b.Property<DateTime>("StartTime");

                    b.Property<string>("UserID")
                        .IsRequired();

                    b.HasKey("ID");

                    b.HasIndex("FightTypeID");

                    b.HasIndex("UserID");

                    b.ToTable("Fights");
                });

            modelBuilder.Entity("web.Models.Fighter", b =>
                {
                    b.Property<int>("ID")
                        .ValueGeneratedOnAdd();

                    b.Property<string>("Avatar");

                    b.Property<DateTime>("BirthDate");

                    b.Property<string>("City");

                    b.Property<string>("Country");

                    b.Property<int>("FighterCategoryID");

                    b.Property<string>("FirstName");

                    b.Property<string>("LastName");

                    b.HasKey("ID");

                    b.HasIndex("FighterCategoryID");

                    b.ToTable("Fighters");
                });

            modelBuilder.Entity("web.Models.FighterCategory", b =>
                {
                    b.Property<int>("ID")
                        .ValueGeneratedOnAdd();

                    b.Property<string>("Name")
                        .IsRequired();

                    b.HasKey("ID");

                    b.ToTable("FighterCategories");
                });

            modelBuilder.Entity("web.Models.FightFighters", b =>
                {
                    b.Property<int>("ID")
                        .ValueGeneratedOnAdd();

                    b.Property<int>("FightID");

                    b.Property<int>("FighterID");

                    b.HasKey("ID");

                    b.HasIndex("FightID");

                    b.HasIndex("FighterID");

                    b.ToTable("FightFighters");
                });

            modelBuilder.Entity("web.Models.FightType", b =>
                {
                    b.Property<int>("ID")
                        .ValueGeneratedOnAdd();

                    b.Property<string>("Name")
                        .IsRequired();

                    b.HasKey("ID");

                    b.ToTable("FightTypes");
                });

            modelBuilder.Entity("web.Models.Hit", b =>
                {
                    b.Property<int>("ID")
                        .ValueGeneratedOnAdd();

                    b.Property<int>("FighterID");

                    b.Property<int>("RoundID");

                    b.Property<DateTime>("Timestamp");

                    b.HasKey("ID");

                    b.HasIndex("FighterID");

                    b.HasIndex("RoundID");

                    b.ToTable("Hits");
                });

            modelBuilder.Entity("web.Models.Round", b =>
                {
                    b.Property<int>("ID")
                        .ValueGeneratedOnAdd();

                    b.Property<DateTime>("EndTime");

                    b.Property<int>("FightID");

                    b.Property<DateTime>("StartTime");

                    b.HasKey("ID");

                    b.HasIndex("FightID");

                    b.ToTable("Round");
                });

            modelBuilder.Entity("Microsoft.AspNetCore.Identity.EntityFrameworkCore.IdentityRoleClaim<string>", b =>
                {
                    b.HasOne("Microsoft.AspNetCore.Identity.EntityFrameworkCore.IdentityRole")
                        .WithMany("Claims")
                        .HasForeignKey("RoleId")
                        .OnDelete(DeleteBehavior.Cascade);
                });

            modelBuilder.Entity("Microsoft.AspNetCore.Identity.EntityFrameworkCore.IdentityUserClaim<string>", b =>
                {
                    b.HasOne("web.Models.ApplicationUser")
                        .WithMany("Claims")
                        .HasForeignKey("UserId")
                        .OnDelete(DeleteBehavior.Cascade);
                });

            modelBuilder.Entity("Microsoft.AspNetCore.Identity.EntityFrameworkCore.IdentityUserLogin<string>", b =>
                {
                    b.HasOne("web.Models.ApplicationUser")
                        .WithMany("Logins")
                        .HasForeignKey("UserId")
                        .OnDelete(DeleteBehavior.Cascade);
                });

            modelBuilder.Entity("Microsoft.AspNetCore.Identity.EntityFrameworkCore.IdentityUserRole<string>", b =>
                {
                    b.HasOne("Microsoft.AspNetCore.Identity.EntityFrameworkCore.IdentityRole")
                        .WithMany("Users")
                        .HasForeignKey("RoleId")
                        .OnDelete(DeleteBehavior.Cascade);

                    b.HasOne("web.Models.ApplicationUser")
                        .WithMany("Roles")
                        .HasForeignKey("UserId")
                        .OnDelete(DeleteBehavior.Cascade);
                });

            modelBuilder.Entity("web.Models.Fight", b =>
                {
                    b.HasOne("web.Models.FightType", "FightType")
                        .WithMany("Fights")
                        .HasForeignKey("FightTypeID")
                        .OnDelete(DeleteBehavior.Cascade);

                    b.HasOne("web.Models.ApplicationUser", "User")
                        .WithMany("Fights")
                        .HasForeignKey("UserID")
                        .OnDelete(DeleteBehavior.Cascade);
                });

            modelBuilder.Entity("web.Models.Fighter", b =>
                {
                    b.HasOne("web.Models.FighterCategory", "FighterCategory")
                        .WithMany("Fighers")
                        .HasForeignKey("FighterCategoryID")
                        .OnDelete(DeleteBehavior.Cascade);
                });

            modelBuilder.Entity("web.Models.FightFighters", b =>
                {
                    b.HasOne("web.Models.Fight", "Fight")
                        .WithMany("FightFighters")
                        .HasForeignKey("FightID")
                        .OnDelete(DeleteBehavior.Cascade);

                    b.HasOne("web.Models.Fighter", "Fighter")
                        .WithMany("FightFighters")
                        .HasForeignKey("FighterID")
                        .OnDelete(DeleteBehavior.Cascade);
                });

            modelBuilder.Entity("web.Models.Hit", b =>
                {
                    b.HasOne("web.Models.Fighter", "Fighter")
                        .WithMany("Hits")
                        .HasForeignKey("FighterID")
                        .OnDelete(DeleteBehavior.Cascade);

                    b.HasOne("web.Models.Round", "Round")
                        .WithMany("Hits")
                        .HasForeignKey("RoundID")
                        .OnDelete(DeleteBehavior.Cascade);
                });

            modelBuilder.Entity("web.Models.Round", b =>
                {
                    b.HasOne("web.Models.Fight", "Fight")
                        .WithMany("Rounds")
                        .HasForeignKey("FightID")
                        .OnDelete(DeleteBehavior.Cascade);
                });
        }
    }
}
