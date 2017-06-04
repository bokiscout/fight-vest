using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using web.DTO;
using web.Models;

namespace web.Hubs
{
    public interface IFightHub
    {
        void OnFightStarted(FightDTO fight);
        void OnFightEnded(FightDTO fight);

        void OnRoundStarted(FightDTO fight);
        void OnRoundEnded(FightDTO fight);

        void OnHit(int fightId, int fighterId, HitDTO hit);
    }
}
