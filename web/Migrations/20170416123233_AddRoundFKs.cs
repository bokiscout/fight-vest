using System;
using System.Collections.Generic;
using Microsoft.EntityFrameworkCore.Migrations;

namespace web.Migrations
{
    public partial class AddRoundFKs : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "FightID",
                table: "Round",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.CreateIndex(
                name: "IX_Round_FightID",
                table: "Round",
                column: "FightID");

            migrationBuilder.AddForeignKey(
                name: "FK_Round_Fights_FightID",
                table: "Round",
                column: "FightID",
                principalTable: "Fights",
                principalColumn: "ID",
                onDelete: ReferentialAction.Cascade);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Round_Fights_FightID",
                table: "Round");

            migrationBuilder.DropIndex(
                name: "IX_Round_FightID",
                table: "Round");

            migrationBuilder.DropColumn(
                name: "FightID",
                table: "Round");
        }
    }
}
