using Microsoft.AspNetCore.SignalR;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace web.Hubs
{
    public class FightHub : Hub<IFightHub>
    {
        private static IDictionary<string, List<int>> fights = new Dictionary<string, List<int>>();

        public FightHub()
        {

        }

        public void JoinFight(int fightId)
        {
            List<int> joinedFights;

            if (fights.TryGetValue(Context.ConnectionId, out joinedFights))
            {
            }
            else
            {
                joinedFights = new List<int>();
            }
            joinedFights.Add(fightId);

            fights.Add(Context.ConnectionId, joinedFights);
        }

        public override Task OnDisconnected(bool stopCalled)
        {
            List<int> joinedFights = new List<int>();
            
            if(fights.TryGetValue(Context.ConnectionId, out joinedFights))
            {
                foreach(int fightId in joinedFights)
                {
                    Groups.Remove(Context.ConnectionId, GetFightKey(fightId));
                }
            }

            return base.OnDisconnected(stopCalled);
        }

        public static string GetFightKey(int id)
        {
            return string.Format("group-{0}", id);
        }
    }
}
