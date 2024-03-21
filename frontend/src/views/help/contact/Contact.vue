<template>
  <div id="main_con" class="main-con" style="margin-top: 30px">
    <div class="comm-h" style="font-size: 22px;font-weight: normal">
      <strong>联系我们</strong>
    </div>
    <div class="wrap-fwfyjs" style="margin-top: 15px;font-family: SimHei">
      <ul class="contact-con">
        <li>
          <span class="hid-txt odpho">服务热线</span>
          <div class="box-ctcat">
            <strong class="sp-d">服务热线:</strong>
            <p class="font">
              <span class="color-rd"><b>400-888-6608</b></span>
            </p>
          </div>
        </li>
        <li>
          <span class="hid-txt fax">公司传真</span>
          <div class="box-ctcat">
            <strong class="sp-d">公司传真:</strong> <span class="color-rd"><b>021-54891121</b></span>
          </div>
        </li>
        <li>
          <span class="hid-txt coop">渠道合作申请邮箱</span>
          <div class="box-ctcat">
            <strong class="sp-d">渠道合作申请邮箱:</strong> <span class="color-rd"><b>Channel_cooperation@1hai.cn</b></span>
          </div>
        </li>
        <li class="oth-w">
          <span class="hid-txt addr">公司地址</span>
          <div class="box-ctcat">
            <strong class="sp-d">公司地址:</strong>
            <p class="sp-f">
              上海总公司：上海市普陀区大渡河路388弄5号国盛中心12层（邮编：200062）
            </p>
          </div>
        </li>
        <li>
          <span class="hid-txt media">媒体联系</span>
          <div class="box-ctcat">
            <strong class="sp-d">媒体联系:</strong>
            <p class="sp-f">
              <img src="https://externalimage.1hai.cn/160/d75907d093b44443b86bd604c2b67941.png" alt="">
            </p>
          </div>
        </li>
      </ul>
    </div>
  </div>
</template>
<script>
export default {
  data () {
    return {
      loading: true,
      article: {
        title: '',
        content: '',
        date: {},
        author: '',
        wc: ''
      },
      today: ''
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
