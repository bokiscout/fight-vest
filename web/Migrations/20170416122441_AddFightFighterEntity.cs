using System;
using System.Collections.Generic;
using Microsoft.EntityFrameworkCore.Migrations;

namespace web.Migrations
{
    public partial class AddFightFighterEntity : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "FightFighters",
                columns: table => new
                {
                    ID = table.Column<int>(nullable: false)
                        .Annotation("MySql:ValueGeneratedOnAdd", true),
                    FightID = table.Column<int>(nullable: false),
                    FighterID = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_FightFighters", x => x.ID);
                    table.ForeignKey(
                        name: "FK_FightFighters_Fights_FightID",
                        column: x => x.FightID,
                        principalTable: "Fights",
                        principalColumn: "ID",
                        onDelete: ReferentialAction.Cascade);
                    table.ForeignKey(
                        name: "FK_FightFighters_Fighters_FighterID",
                        column: x => x.FighterID,
                        principalTable: "Fighters",
                        principalColumn: "ID",
                        onDelete: ReferentialAction.Cascade);
                });

            migrationBuilder.CreateIndex(
                name: "IX_FightFighters_FightID",
                table: "FightFighters",
                column: "FightID");

            migrationBuilder.CreateIndex(
                name: "IX_FightFighters_FighterID",
                table: "FightFighters",
                column: "FighterID");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "FightFighters");
        }
    }
}
