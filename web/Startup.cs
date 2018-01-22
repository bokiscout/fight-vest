using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.Identity.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Logging;
using Microsoft.EntityFrameworkCore;
using web.Models;
using web.Services;
using Newtonsoft.Json;
using Newtonsoft.Json.Serialization;
using AutoMapper;
using Microsoft.AspNetCore.Http;
using System.Linq;
using System.Collections.Generic;

namespace web
{
    public class Startup
    {
        public Startup(IHostingEnvironment env)
        {
            var builder = new ConfigurationBuilder()
                .SetBasePath(env.ContentRootPath)
                .AddJsonFile("appsettings.json", optional: false, reloadOnChange: true)
                .AddJsonFile($"appsettings.{env.EnvironmentName}.json", optional: true)
                .AddEnvironmentVariables();
            Configuration = builder.Build();
        }

        public IConfigurationRoot Configuration { get; }

        // This method gets called by the runtime. Use this method to add services to the container.
        public void ConfigureServices(IServiceCollection services)
        {
            var appSettings = Configuration.GetSection("AppSettings");
            services.Configure<AppSettings>(appSettings);

            services.AddDbContext<ApplicationDbContext>(options =>{
                options.UseMySql(Configuration.GetConnectionString("DefaultConnection"));
            });

            services.AddIdentity<ApplicationUser, IdentityRole>()
                .AddEntityFrameworkStores<ApplicationDbContext>()
                .AddDefaultTokenProviders();

            services.AddTransient<IMessageService, DummyMessageService>();

            services.Configure<IdentityOptions>(options => {
                options.Password.RequireDigit = false;
                options.Password.RequiredLength = 6;
                options.Password.RequireLowercase = false;
                options.Password.RequireNonAlphanumeric = false;
                options.Password.RequireUppercase = false;                
            });

            // Add framework services.
            services.AddMvc()
               .AddJsonOptions(options => {
                   options.SerializerSettings.ReferenceLoopHandling = ReferenceLoopHandling.Ignore;
                   options.SerializerSettings.DateFormatHandling = DateFormatHandling.IsoDateFormat;

                   if (options.SerializerSettings.ContractResolver != null)
                   {
                       var castedResolver = options.SerializerSettings.ContractResolver
                           as DefaultContractResolver;
                       castedResolver.NamingStrategy = null;
                   }
               });

            services.AddSignalR(options =>
            {
                options.Hubs.EnableDetailedErrors = true;
                options.EnableJSONP = true;
            });

            services.AddSingleton<IHttpContextAccessor, HttpContextAccessor>();
            services.AddAutoMapper();
        }

        // This method gets called by the runtime. Use this method to configure the HTTP request pipeline.
        public void Configure(IApplicationBuilder app, IHostingEnvironment env, ILoggerFactory loggerFactory, ApplicationDbContext dbContext)
        {
            loggerFactory.AddConsole(Configuration.GetSection("Logging"));
            loggerFactory.AddDebug();

            if (env.IsDevelopment())
            {
                app.UseDeveloperExceptionPage();
                app.UseBrowserLink();
                SeedDatabase(dbContext);
            }
            else
            {
                app.UseExceptionHandler("/Home/Error");
            }

            app.UseIdentity();
            app.UseStaticFiles();

            app.UseMvc(routes =>
            {
                routes.MapRoute(
                    name: "default",
                    template: "{controller=Home}/{action=Index}/{id?}");
            });

            app.Map("/signalr", options => {

                 // Setup the CORS middleware to run before SignalR.
                 // By default this will allow all origins. You can 
                 // configure the set of origins and/or http verbs by
                 // providing a cors options with a different policy.

                 //options.UseCors(CorsOptions.AllowAll);

                 options.UseWebSockets();
                 options.RunSignalR();
             });
        }

        private void SeedDatabase(ApplicationDbContext context)
        {
            context.Database.Migrate();

            if(!context.FighterCategories.Any())
            {
                var categories = new List<FighterCategory>()
                {
                    new FighterCategory
                    {
                        Name = "Lightweight"
                    },
                    new FighterCategory
                    {
                        Name = "Featherweight"
                    },
                    new FighterCategory
                    {
                        Name = "Welterweight"
                    },
                    new FighterCategory
                    {
                        Name = "Heavyweight"
                    }   
                };

                categories.ForEach(c => context.FighterCategories.Add(c));
                context.SaveChanges();
            }
        }
    }
}
