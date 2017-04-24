using System;
using System.Collections.Generic;
using Microsoft.EntityFrameworkCore.Migrations;

namespace web.Migrations
{
    public partial class FightLocation : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "Address",
                table: "Fights",
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "City",
                table: "Fights",
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "Counry",
                table: "Fights",
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "Address",
                table: "Fights");

            migrationBuilder.DropColumn(
                name: "City",
                table: "Fights");

            migrationBuilder.DropColumn(
                name: "Counry",
                table: "Fights");
        }
    }
}
