<template>
  <a-collapse v-model="activeKey" :bordered="false" style="width: 100%;margin-top: 30px;">
    <a-collapse-panel key="1" header="Step1 注册登录">
      <p>在享受一嗨的便捷服务前，首先您需要注册成为一嗨会员，在完成注册并登录账号后，即可开始进行车辆预订。

        注册步骤如下：
        1、点击页面左上角“免费注册”；

        2、在会员注册页面中正确填写您的手机号、短信验证码等信息；

        3、设置账户密码；

        4、点击“注册”按钮即可完成注册。</p>
    </a-collapse-panel>
    <a-collapse-panel key="2" header="Step2 预订车辆" :disabled="false">
      <p>1、选择用车时间地点
        登录https://www.1hai.cn/，在首页选择取还车时间、城市、门店，点击“立即选车”进入选车页面。


        2、选择车型
        进入选车页面后，可根据您的用车需求选择车型和价格类型。


        3、订单明细页面操作
        点击预订后，进入提交订单页面：


        (1)选择支付方式：目前支持线下支付、在线支付、代金券支付、余额支付等支付方式。

        (2)选择优惠活动：含优惠券及其它优惠活动，可根据您的不同需求选择。

        (3)选择其它服务：除基础车辆租赁服务外，还有其它增值服务，可根据您的需求选择。

        (4)发票信息：如需发票，点击“添加发票”，填写相应信息。请进一步确认订单信息及费用。


        (5)备注信息：如有其它需求，可添加备注，填写具体需求。

        (6)完成订单：点击“去支付”，完成支付后即成功预订用车。我们将会为您准备所预订的车辆，请妥善保管您的订单号，一嗨祝您自驾旅途顺利</p>
    </a-collapse-panel>
    <a-collapse-panel key="3" header="Step3 取车还车">
      <p>第一次取车准备：
        取车时请务必携带本人有效二代身份证原件，本人国内驾驶证原件（正副页），本人信用卡。


        境内人士：
        (1)本人二代有效身份证

        (2)本人国内驾驶证原件（正副页）/电子驾照（部队驾驶证不可使用）

        (3)部分情况下还需提供其它类似效力的相关材料

        外籍人士：
        (1)本人中国有效驾驶证原件（正副页）

        (2)本人有效居住证（在中国居住者）

        (3)本人有效护照

        (4)本人国际/国内信用卡（如果单张额度不够，可以多张担保）

        取车步骤：
        (1)确认订单后，请携带以上规定证照至订单取车门店办理手续；

        (2)交接车辆时，请在门店工作人员陪同下验车，并填写验车单，确认并签字（此验车单将作为您返还车辆时验车的凭证）；

        (3)验车完成后，请支付租车费用及刷取违章押金（如已选择芝麻信用免押或其它免押除外）；

        (4)完成以上手续后，即可开始您的驾乘之旅。一嗨祝您的自驾旅途愉快顺利！旅途中请安全驾驶，温馨提示：异地违章处理成本会较高，12分的违章可能导致驾照吊销。

        友情提示：
        (1)服务热线

        您在使用过程中遇到任何问题，可致电一嗨7*24小时客服：400-888-6608，我们将为您提供专业指引，并全程跟进，让您无后顾之忧。

        (2)投诉

        如果您对我们的服务有不满意，或者想提出建议，可致电一嗨投诉中心：400-821-1608（周一至周日：9:00-18:00），或在www.1hai.cn的投诉中心在线提交，我们的投诉专员会尽快为您处理。

        (3)避开高峰取车还车

        由于周末和节假日租车需求比较旺盛，若有重要事宜，请尽量避开周末及节假日等高峰时段取车或还车。

        (4)新用户

        首次租车的新用户，取车时需提供本人信用卡、本人二代有效身份证、本人驾驶证原件（正副页），机场店取车需提供本人当天登机牌或机票，部分情况下还需提供其它类似效力的相关材料。

        (5)客户信息变更

        租车需至法定驾驶年龄，且当客户的相关身份（如姓名、性别、身份证号等）、住所、联系方式、驾驶资质变更等信息发生变化时，应当及时书面通知一嗨，否则自行承担相应法律后果。</p>
    </a-collapse-panel>
  </a-collapse>
</template>
<script>
export default {
  data () {
    return {
      activeKey: ['1', '2', '3', '4', '5', '6', '7'],
      loading: true,
      text: `A dog is a type of domesticated animal.Known for its loyalty and faithfulness,it can be found as a welcome guest in many households across the world.`,
      customStyle:
        'background: #f7f7f7;border-radius: 4px;margin-bottom: 24px;border: 0;overflow: hidden'
    }
  },
  methods: {
  },
  mounted () {
  }
}
</script>
<style lang="less">
.daily-article {
  .article-button {
    font-size: 1.2rem !important;
  }
  .ant-card-body {
    padding: 18px !important;
  }
  .ant-card-head {
    padding: 0 1rem;
  }
  .ant-card-meta {
    margin-bottom: 1rem;
  }
  .article-content {
    p {
      word-wrap: break-word;
      word-break: break-all;
      text-overflow: initial;
      white-space: normal;
      font-size: .9rem !important;
      margin-bottom: .8rem;
    }
  }
}
</style>
