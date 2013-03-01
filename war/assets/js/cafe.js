;
$(document).ready(function() {
    console.log("processing cafe scripts");
    // jquery validate plugins
    $.validator.addMethod("money", function (value, element) {
      return this.optional(element) || value.match(/^\d+(\.(\d{2}))?$/);
    }, "Please provide a valid dollar amount (up to 2 decimal places) and do not include a dollar sign.");
    
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
            .addClass('valid')
//            .text('OK!').addClass('valid')
            .closest('.control-group').addClass('success');
        }
    });
    $("#vendorItemForm").validate({
        rules: {
            name: {
                required: true,
            },
        },
        highlight: function(label) {
            $(label).closest('.control-group').addClass('error');
        },
//        success: function(label) {
//            label
//            .addClass('valid')
//            .closest('.control-group').addClass('success');
//        }
    });
    
    //phoneUS: true
    
    // controls the measures selectors on the vendorItemForm
    $('#vendorItemForm select[name=measureType]').change(function() {
        var type = $(this).val();
        console.log('Handler for .change() called with ' + type);
        // hide everything to start
        hideAllVendorFormMeasurementItems();
        // now selectively show the correct one
        if(type == 'g') {
            // show the weight ones
            $('label[for="weight"]').show();
            $('label[for="weightLost"]').show();
            $('input[name="weight"]').show().addClass('required');
            $('input[name="weightLost"]').show().addClass('required');
            $('select[name="weightUnit"]').show().addClass('required');
            $('select[name="weightPackage"]').show().addClass('required');
            $('select[name="weightLostUnit"]').show().addClass('required');
        } else if(type == 'num') {
            $('label[for="count"]').show();
            $('input[name="count"]').show().addClass('required');
        } else if(type == 'ml') {
            $('label[for="volume"]').show();
            $('label[for="volumeLost"]').show();
            $('input[name="volume"]').show().addClass('required');
            $('select[name="volumePackage"]').show().addClass('required');
            $('input[name="volumeLost"]').show().addClass('required');
            $('select[name="volumeUnit"]').show().addClass('required');
            $('select[name="volumeLostUnit"]').show().addClass('required');
        }
    });   
    
    function hideAllVendorFormMeasurementItems(){
        $('label[for="count"]').hide();
        $('label[for="weight"]').hide();
        $('label[for="weightLost"]').hide();
        $('label[for="volume"]').hide();
        $('label[for="volumeLost"]').hide();
        $('input[name="weight"]').hide().removeClass('required');
        $('input[name="weightLost"]').hide().removeClass('required');
        $('input[name="count"]').hide().removeClass('required');
        $('input[name="volume"]').hide().removeClass('required');
        $('input[name="volumeLost"]').hide().removeClass('required');
        $('select[name="weightUnit"]').hide().removeClass('required');
        $('select[name="weightPackage"]').hide().addClass('required');
        $('select[name="weightLostUnit"]').hide().removeClass('required');
        $('select[name="volumeUnit"]').hide().removeClass('required');
        $('select[name="volumePackage"]').hide().addClass('required');
        $('select[name="volumeLostUnit"]').hide().removeClass('required');
    }
});