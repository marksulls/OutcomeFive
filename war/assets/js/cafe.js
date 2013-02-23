;
$(document).ready(function() {
    console.log("processing cafe scripts");
    $("#registrationForm").validate({
        rules: {
            password: {
              required: true,
            },
            name: {
                required: true,
            },
            cafeName: {
                required: true,
            },
            confirm: {
              equalTo: "#password"
            },
        },
        highlight: function(label) {
          $(label).closest('.control-group').addClass('error');
        },
        success: function(label) {
          label
            .text('OK!').addClass('valid')
            .closest('.control-group').addClass('success');
        }
    });
});