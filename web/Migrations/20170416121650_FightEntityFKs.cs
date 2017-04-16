using System;
using System.Collections.Generic;
using Microsoft.EntityFrameworkCore.Migrations;

namespace web.Migrations
{
    public partial class FightEntityFKs : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "UserID",
                table: "Fights",
                nullable: false,
                defaultValue: "");

            migrationBuilder.CreateIndex(
                name: "IX_Fights_UserID",
                table: "Fights",
                column: "UserID");

            migrationBuilder.AddForeignKey(
                name: "FK_Fights_AspNetUsers_UserID",
                table: "Fights",
                column: "UserID",
                principalTable: "AspNetUsers",
                principalColumn: "Id",
                onDelete: ReferentialAction.Cascade);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Fights_AspNetUsers_UserID",
                table: "Fights");

            migrationBuilder.DropIndex(
                name: "IX_Fights_UserID",
                table: "Fights");

            migrationBuilder.DropColumn(
                name: "UserID",
                table: "Fights");
        }
    }
}
