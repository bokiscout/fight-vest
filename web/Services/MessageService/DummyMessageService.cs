using System;
using System.Threading.Tasks;

namespace web.Services
{
    public class DummyMessageService : IMessageService
    {
        public Task Send(string email, string subject, string message)
        {
            return Task.FromResult(0);
        }
    }
}