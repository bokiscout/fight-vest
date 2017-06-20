using System;
using System.Collections.Generic;
using Microsoft.EntityFrameworkCore.Migrations;

namespace web.Migrations
{
    public partial class AddHitType : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "Type",
                table: "Hits",
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "Type",
                table: "Hits");
        }
    }
}
