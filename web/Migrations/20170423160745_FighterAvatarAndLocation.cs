using System;
using System.Collections.Generic;
using Microsoft.EntityFrameworkCore.Migrations;

namespace web.Migrations
{
    public partial class FighterAvatarAndLocation : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "Avatar",
                table: "Fighters",
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "City",
                table: "Fighters",
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "Country",
                table: "Fighters",
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "Avatar",
                table: "Fighters");

            migrationBuilder.DropColumn(
                name: "City",
                table: "Fighters");

            migrationBuilder.DropColumn(
                name: "Country",
                table: "Fighters");
        }
    }
}
