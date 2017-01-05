//store the intersection logic
//javascript module
var seckill ={
    //Packaging kill related ajax the url
    URL: {
        now : function(){
            return '/seckill/time/now';
        },
        exposer : function(seckillId){
            return '/seckill/'+seckillId+'/exposer';
        },
        execution : function(seckillId,md5){
            return '/seckill/'+seckillId+'/'+md5+'/execution';
        }
    },
    handleSeckillkill : function(seckillId,node){
        //Get spike address, control the display logic, the implementation of spike
        node.hide()
            .html('<button class="btn btn-primary btn-lg" id="killBtn">Start Kill</button>');//btn
        $.post(seckill.URL.exposer(seckillId),{},function(result){
            //In the callback function, the interaction flow is executed
            if(result && result['success']){
                var exposer = result['data'];
                if(exposer['exposed']){
                    //START KILL
                    //GET KILL ADDRESS.
                    var md5 = exposer['md5'];
                    var killUrl = seckill.URL.execution(seckillId,md5);
                    console.log("killUrl:"+killUrl);
                    //BINDING ON TIME CLICKING
                    $('#killBtn').one('click',function(){
                        //PERFORM KILL
                        //1:DISABLE BTN
                        $(this).addClass('disabled');
                        //2:Send KILL request to perform spike
                        $.post(killUrl,{},function(result){
                            if(result && result['success']){
                                var killResult = result['data'];
                                var state = killResult['state'];
                                var stateInfo = killResult['stateInfo'];
                                //3:SHOW KILL RESULT
                                node.html('<span class="label label-success">' + stateInfo + '</span>');
                            }
                        });
                    });
                    node.show();
                } else {
                    //not start killing
                    var now = exposer['now'];
                    var start = exposer['start'];
                    var end = exposer['end'];
                    //Recalculate timing logic
                    seckill.countdown(seckillId, now, start, end);
                }
            }else{
                console.log('result:'+result);
            }

        });
    },
    //验证手机号
    validatePhone: function (phone) {
        if (phone && phone.length == 11 && !isNaN(phone)) {
            return true;
        } else {
            return false;
        }
    },
    countdown: function (seckillId, nowTime, startTime, endTime) {
        var seckillBox = $('#seckill-box');
        //TIME DETERMINE
        if(nowTime > endTime){
            //KILL END
            seckillBox.html('KILL FINISH!');
        }else if(nowTime < startTime){
            //KILL not started, timed event binding
            var killTime = new Date(startTime + 1000);
            seckillBox.countdown(killTime,function(event){
                //TIME FORMAT
                var format = event.strftime('kill countdown: %D DAY %H HOUR %M MINUTE %S SECOND');
                seckillBox.html(format);
                /*AFTER TIME ROLLBACK FUNCTION*/
            }).on('finish.countdown',function(){

                seckill.handleSeckillkill(seckillId,seckillBox);
            });
        }else{
            //秒杀开始
            seckill.handleSeckillkill(seckillId,seckillBox);
        }
    },
    //detail kill logic
    detail: {
        //detail start up page
        init : function(params){
            //Mobile authentication and login, timing interaction
            //Plan our interactive process
            //Find the phone number in the cookie
            var killPhone = $.cookie('killPhone');
            //VERIFY PHONE NUMBER
            if(!seckill.validatePhone(killPhone)){
                //BINDING phone
                //CONTROL TIME
                var killPhoneModal = $('#killPhoneModal');
                //show pop up
                killPhoneModal.modal({
                    show: true,//SHOW POP UP WINDOW
                    backdrop: 'static',// disabled position turned off
                    keyboard: false//TRUN OFF KEYBOARY
                });
                $('#killPhoneBtn').click(function(){
                    var inputPhone = $('#killPhoneKey').val();
                    console.log('inputPhone='+inputPhone);//TODO
                    if(seckill.validatePhone(inputPhone)){
                        //Phone writing cookie
                        $.cookie('killPhone', inputPhone, {expires: 7, path: '/seckill'});
                        //refresh page
                        window.location.reload();
                    }else{
                        $('#killPhoneMessage').hide().html('<label class="label label-danger">phone number error!</label>').show(300);
                    }
                });
            }
            //already login in
            //Timing interaction
            var startTime = params['startTime'];
            var endTime = params['endTime'];
            var seckillId = params['seckillId'];
            $.get(seckill.URL.now(), {}, function (result) {
                if(result && result['success']){
                    var nowTime = result['data'];
                    //Time judgment, timing interaction
                    seckill.countdown(seckillId,nowTime,startTime,endTime);
                }else{
                    console.log('result:'+result);
                }
            });


        }
    }
}