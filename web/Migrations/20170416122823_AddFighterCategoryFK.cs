using System;
using System.Collections.Generic;
using Microsoft.EntityFrameworkCore.Migrations;

namespace web.Migrations
{
    public partial class AddFighterCategoryFK : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "FighterCategoryID",
                table: "Fighters",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AlterColumn<string>(
                name: "Name",
                table: "FighterCategories",
                nullable: false,
                oldClrType: typeof(string),
                oldNullable: true);

            migrationBuilder.CreateIndex(
                name: "IX_Fighters_FighterCategoryID",
                table: "Fighters",
                column: "FighterCategoryID");

            migrationBuilder.AddForeignKey(
                name: "FK_Fighters_FighterCategories_FighterCategoryID",
                table: "Fighters",
                column: "FighterCategoryID",
                principalTable: "FighterCategories",
                principalColumn: "ID",
                onDelete: ReferentialAction.Cascade);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Fighters_FighterCategories_FighterCategoryID",
                table: "Fighters");

            migrationBuilder.DropIndex(
                name: "IX_Fighters_FighterCategoryID",
                table: "Fighters");

            migrationBuilder.DropColumn(
                name: "FighterCategoryID",
                table: "Fighters");

            migrationBuilder.AlterColumn<string>(
                name: "Name",
                table: "FighterCategories",
                nullable: true,
                oldClrType: typeof(string));
        }
    }
}
