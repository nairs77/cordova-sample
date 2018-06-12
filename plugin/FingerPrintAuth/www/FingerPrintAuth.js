var exec = require('cordova/exec');

exports.coolMethod = function (arg0, success, error) {
    exec(success, error, 'FingerPrintAuth', 'coolMethod', [arg0]);
};

module.exports.isFingerPrintAvailable = function(arg0, success, error) {
    exec(success, error, 'FingerPrintAuth', 'isFingerPrintAvailable', [arg0]);
}
module.exports.startAuth = function(arg0, success, error) {
    exec(success, error, 'FingerPrintAuth', 'startAuth', [arg0]);
}
module.exports.stopAuth = function(arg0, success, error) {
    exec(success, error, 'FingerPrintAuth', 'stopAuth', [arg0]);
}

// FingerPrintAuth.prototype.isFingerPrintAvailable = function (successCallback, errorCallback) {
//     cordova.exec(
//         successCallback,
//         errorCallback,
//         "FingerPrintAuth",  // Java Class
//         "isFingerPrintAvailable", // action
//         [ // Array of arguments to pass to the Java class
//             {}
//         ]        
//     );
// };

// FingerPrintAuth.prototype.startAuth = function(successCallback, errorCallback) {
//     cordova.exec(
//         successCallback,
//         errorCallback,
//         "FingerPrintAuth",  // Java Class
//         "startAuth", // action
//         [ // Array of arguments to pass to the Java class
//             {}
//         ]        
//     );
// };

// FingerPrintAuth.prototype.delete = function (params, successCallback, errorCallback) {
//     cordova.exec(
//         successCallback,
//         errorCallback,
//         "FingerPrintAuth",  // Java Class
//         "stopAuth", // action
//         [ // Array of arguments to pass to the Java class
//             {}
//         ]
//     );
// };

// FingerPrintAuth.prototype.isAvailable = function (successCallback, errorCallback) {
//     cordova.exec(
//         successCallback,
//         errorCallback,
//         "FingerprintAuth",  // Java Class
//         "availability", // action
//         [{}]
//     );
// };

// FingerPrintAuth = new FingerPrintAuth();
// module.exports = FingerPrintAuth;