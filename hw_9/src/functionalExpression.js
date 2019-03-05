"use strict"

var vars = {
    "x": 0,
    "y": 1,
    "z": 2
};

function binaryOperation(operation) {
    return function () {
        var op = arguments;
        return function () {
            var res = [];
            for (let i = 0; i < op.length; ++i) {
                res.push(op[i].apply(null, arguments));
            }
            return operation.apply(null, res);
        }
    }
}

var subtract = binaryOperation(
    function (a, b) {
        return a - b;
    }
);

var multiply = binaryOperation(
    function (a, b) {
        return a * b;
    }
);

var variable = function (name) {
    return function () {
        return arguments[vars[name]];
    }
}

var cnst = function (a) {
    return function () {
        return a;
    };
};

var expr = subtract(
    multiply(
        cnst(2),
        variable("x")
    ),
    cnst(3)
);

document.write(expr(5));
