using System.Threading.Tasks;

namespace web.Services
{
    public interface IMessageService
    {
        Task Send(string email, string subject, string message);
    }
}