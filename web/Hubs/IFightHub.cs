using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using web.Models;

namespace web.Hubs
{
    public interface IFightHub
    {
        void OnFightStarted(int fightId);
        void OnFightEnded(int fightId);

        void OnRoundStarted(int roundNumber);
        void OnRoundEnded(int roundNumber);

        void OnHit(int fightId, Fighter fighter);
    }
}
