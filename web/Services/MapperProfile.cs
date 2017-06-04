using AutoMapper;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using web.DTO;
using web.Models;

namespace web.Services
{
    public class MapperProfile : Profile
    {
        public MapperProfile()
        {
            CreateMap<Hit, HitDTO>();
            CreateMap<Round, RoundDTO>();
            CreateMap<FightType, FightTypeDTO>();
            CreateMap<FighterCategory, FighterCategoryDTO>();
            CreateMap<Fighter, FighterDTO>();
            CreateMap<Fight, FightDTO>();
            CreateMap<FightFighters, FightFightersDTO>();
        }
    }
}
