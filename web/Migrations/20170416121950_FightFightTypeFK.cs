using System;
using System.Collections.Generic;
using Microsoft.EntityFrameworkCore.Migrations;

namespace web.Migrations
{
    public partial class FightFightTypeFK : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "FightTypeID",
                table: "Fights",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.CreateIndex(
                name: "IX_Fights_FightTypeID",
                table: "Fights",
                column: "FightTypeID");

            migrationBuilder.AddForeignKey(
                name: "FK_Fights_FightTypes_FightTypeID",
                table: "Fights",
                column: "FightTypeID",
                principalTable: "FightTypes",
                principalColumn: "ID",
                onDelete: ReferentialAction.Cascade);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Fights_FightTypes_FightTypeID",
                table: "Fights");

            migrationBuilder.DropIndex(
                name: "IX_Fights_FightTypeID",
                table: "Fights");

            migrationBuilder.DropColumn(
                name: "FightTypeID",
                table: "Fights");
        }
    }
}
