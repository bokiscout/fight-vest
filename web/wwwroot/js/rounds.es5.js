"use strict";

var Hit = React.createClass({
    displayName: "Hit",

    render: function render() {
        var hit = this.props.hit;
        var round = this.props.round;
        var roundDuration = this.props.roundDuration;

        var secondsToHit = this.calculateSeconds(round.StartedAt, hit.Timestamp);
        var left = secondsToHit * 100 / roundDuration;
        console.log(secondsToHit);

        var style = {
            left: left + "%"
        };

        return React.createElement("span", { className: "round-point", style: style }, React.createElement("i", { className: "fa fa-hand-rock-o" }));
    },
    calculateSeconds: function calculateSeconds(date1, date2) {
        var t1 = new Date(date1);
        var t2 = new Date(date2);
        var dif = t1.getTime() - t2.getTime();

        var Seconds_from_T1_to_T2 = dif / 1000;
        var Seconds_Between_Dates = Math.abs(Seconds_from_T1_to_T2);

        return Seconds_Between_Dates;
    }
});

var Round = React.createClass({
    displayName: "Round",

    render: function render() {
        var round = this.props.round;
        var duration = this.calculateSeconds(round.StartedAt, round.EndedAt || new Date());

        var hits = this.props.round.Hits.map(function (hit) {
            return React.createElement(Hit, { key: hit.ID, hit: hit, round: round, roundDuration: duration });
        });

        return React.createElement("div", { className: "progress" }, React.createElement("div", { className: "progress-bar progress-bar-info round-container w-full" }, hits));
    },
    calculateSeconds: function calculateSeconds(date1, date2) {
        var t1 = new Date(date1);
        var t2 = new Date(date2);
        var dif = t1.getTime() - t2.getTime();

        var Seconds_from_T1_to_T2 = dif / 1000;
        var Seconds_Between_Dates = Math.abs(Seconds_from_T1_to_T2);

        return Seconds_Between_Dates;
    }
});

var Rounds = React.createClass({
    displayName: "Rounds",

    render: function render() {
        var rounds = this.props.rounds;

        var result = rounds.map(function (round) {
            return React.createElement(Round, { round: round, key: round.ID });
        });

        return React.createElement("div", null, result);
    }
});

