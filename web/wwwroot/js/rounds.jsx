var Hit = React.createClass({
    render: function () {
        var hit = this.props.hit;
        var round = this.props.round;
        var roundDuration = this.props.roundDuration;
        var firstFighterId = this.props.firstFighterId;

        var secondsToHit = this.calculateSeconds(round.StartTime, hit.Timestamp);
        var left = (secondsToHit * 100) / roundDuration;

        var style = {
            left: left + "%"
        };

        var spanClass = "round-point label ";
        spanClass += (hit.FighterID == firstFighterId ? "label-danger" : "label-warning");

        return (
            <span className={spanClass} style={style}>
                <i className="fa fa-hand-rock-o"></i>
            </span>
            );
    },
    calculateSeconds: function (date1, date2) {
        var t1 = new Date(date1);
        var t2 = new Date(date2);
        var dif = t1.getTime() - t2.getTime();

        var Seconds_from_T1_to_T2 = dif / 1000;
        var Seconds_Between_Dates = Math.abs(Seconds_from_T1_to_T2);

        return Seconds_Between_Dates;
    }
});

var Round = React.createClass({
    render: function () {
        var round = this.props.round;
        var duration = this.calculateSeconds(round.StartTime, round.EndTime || new Date());
        var firstFighterId = this.props.firstFighterId;
        var roundNumber = this.props.roundNumber;

        var hits = this.props.round.Hits.map(function (hit) {
            return <Hit key={hit.ID} hit={hit} round={round} roundDuration={duration} firstFighterId={firstFighterId}/>
        });


        var progressClass = "progress-bar progress-bar-success round-container w-full ";
        if (round.EndTime != null) {
            progressClass += "progress-bar-striped";
        }

        return (
            <div>
                <div className="col-xs-3 col-sm-2">
                    <b>Round {roundNumber}</b>
                </div>
                <div className="col-xs-9 col-sm-10">
                    <div className="progress">
                        <div className={progressClass}>
                            {hits}
                        </div>
                    </div>
                </div>
                
            </div>
            );
    },
    calculateSeconds: function (date1, date2) {
        var t1 = new Date(date1);
        var t2 = new Date(date2);
        var dif = t1.getTime() - t2.getTime();

        var Seconds_from_T1_to_T2 = dif / 1000;
        var Seconds_Between_Dates = Math.abs(Seconds_from_T1_to_T2);

        return Seconds_Between_Dates;
    }
});

var Rounds = React.createClass({
    render: function () {
        var rounds = this.props.rounds;
        var firstFighterId = this.props.firstFighterId;
        var roundNumber = rounds.length;

        var result = [];
        for (var i = rounds.length - 1; i >= 0; i--) {
            var round = rounds[i];
            result.push(<Round round={round} key={round.ID} firstFighterId={firstFighterId} roundNumber={roundNumber--} />);
        }

        return (
                <div>
                    {result}
                </div>
            );
    }
});