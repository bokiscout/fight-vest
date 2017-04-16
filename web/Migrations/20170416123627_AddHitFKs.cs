using System;
using System.Collections.Generic;
using Microsoft.EntityFrameworkCore.Migrations;

namespace web.Migrations
{
    public partial class AddHitFKs : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "FighterID",
                table: "Hits",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "RoundID",
                table: "Hits",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.CreateIndex(
                name: "IX_Hits_FighterID",
                table: "Hits",
                column: "FighterID");

            migrationBuilder.CreateIndex(
                name: "IX_Hits_RoundID",
                table: "Hits",
                column: "RoundID");

            migrationBuilder.AddForeignKey(
                name: "FK_Hits_Fighters_FighterID",
                table: "Hits",
                column: "FighterID",
                principalTable: "Fighters",
                principalColumn: "ID",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_Hits_Round_RoundID",
                table: "Hits",
                column: "RoundID",
                principalTable: "Round",
                principalColumn: "ID",
                onDelete: ReferentialAction.Cascade);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Hits_Fighters_FighterID",
                table: "Hits");

            migrationBuilder.DropForeignKey(
                name: "FK_Hits_Round_RoundID",
                table: "Hits");

            migrationBuilder.DropIndex(
                name: "IX_Hits_FighterID",
                table: "Hits");

            migrationBuilder.DropIndex(
                name: "IX_Hits_RoundID",
                table: "Hits");

            migrationBuilder.DropColumn(
                name: "FighterID",
                table: "Hits");

            migrationBuilder.DropColumn(
                name: "RoundID",
                table: "Hits");
        }
    }
}
