<template>
  <a-modal v-model="show" title="订单评价" @cancel="onClose" :width="800">
    <template slot="footer">
      <a-button key="back" @click="onClose">
        取消
      </a-button>
      <a-button key="submit" type="primary" :loading="loading" @click="handleSubmit">
        提交
      </a-button>
    </template>
    <div style="font-size: 13px;font-family: SimHei" v-if="orderData !== null">
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">基础信息</span></a-col>
        <a-col :span="8"><b>订单编号：</b>
          {{ orderData.code }}
        </a-col>
        <a-col :span="8"><b>订单名称：</b>
          {{ orderData.orderName ? orderData.orderName : '- -' }}
        </a-col>
        <a-col :span="8"><b>价格/日：</b>
          {{ orderData.dayPrice ? orderData.dayPrice + '元' : '- -' }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>开始租车时间：</b>
          {{ orderData.startDate }}
        </a-col>
        <a-col :span="8"><b>归还车辆时间：</b>
          {{ orderData.endDate ? orderData.endDate : '- -' }}
        </a-col>
        <a-col :span="8"><b>总价格：</b>
          {{ orderData.total ? orderData.total + '元' : '- -' }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>订单状态：</b>
          <span v-if="orderData.status == -1">未支付</span>
          <span v-if="orderData.status == 0">未完成</span>
          <span v-if="orderData.status == 1">已完成</span>
        </a-col>
        <a-col :span="8"><b>取车店铺：</b>
          {{ orderData.takeShop ? orderData.takeShop : '- -' }}
        </a-col>
        <a-col :span="8"><b>归还店铺：</b>
          {{ orderData.returnShop ? orderData.returnShop : '- -' }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="8"><b>下单时间：</b>
          {{ orderData.createDate ? orderData.createDate : '- -' }}
        </a-col>
        <a-col :span="8"><b>备注：</b>
          {{ orderData.remark ? orderData.remark : '- -' }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;" v-if="vehicleInfo != null">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">车辆信息</span></a-col>
        <a-col :span="8"><b>车辆编号：</b>
          {{ vehicleInfo.vehicleNo }}
        </a-col>
        <a-col :span="8"><b>车牌号：</b>
          {{ vehicleInfo.vehicleNumber }}
        </a-col>
        <a-col :span="8"><b>车辆颜色：</b>
          {{ vehicleInfo.vehicleColor }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;" v-if="vehicleInfo != null">
        <a-col :span="8"><b>车辆名称：</b>
          {{ vehicleInfo.name }}
        </a-col>
        <a-col :span="8"><b>发动机号码：</b>
          {{ vehicleInfo.engineNo }}
        </a-col>
        <a-col :span="8"><b>载客数量：</b>
          {{ vehicleInfo.carryPassengers }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;" v-if="vehicleInfo != null">
        <a-col :span="8"><b>负责人：</b>
          {{ vehicleInfo.principal }}
        </a-col>
        <a-col :span="8"><b>联系电话：</b>
          {{ vehicleInfo.phone }}
        </a-col>
        <a-col :span="8"><b>出场日期：</b>
          {{ vehicleInfo.factoryDate }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;" v-if="vehicleInfo != null">
        <a-col :span="8"><b>排放标准：</b>
          {{ vehicleInfo.emissionStandard }}
        </a-col>
        <a-col :span="8"><b>燃料类型：</b>
          <span v-if="vehicleInfo.fuelType == 1">燃油</span>
          <span v-if="vehicleInfo.fuelType == 2">柴油</span>
          <span v-if="vehicleInfo.fuelType == 3">油电混动</span>
          <span v-if="vehicleInfo.fuelType == 4">电能</span>
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;" v-if="userInfo !== null">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">用户信息</span></a-col>
        <a-col :span="8"><b>客户编号：</b>
          {{ userInfo.code }}
        </a-col>
        <a-col :span="8"><b>客户名称：</b>
          {{ userInfo.name }}
        </a-col>
        <a-col :span="8"><b>联系方式：</b>
          {{ userInfo.phone }}
        </a-col>
      </a-row>
    </div>
    <a-form :form="form" layout="vertical" style="padding-left: 24px;padding-right: 24px;">
      <a-row :gutter="20">
        <a-col :span="6">
          <a-form-item label='评价得分' v-bind="formItemLayout">
            <a-input-number style="width: 100%" v-decorator="[
            'score',
            { rules: [{ required: true, message: '请输入得分!' }] }
            ]" :min="1" :max="100" :step="0.1"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label='评价内容' v-bind="formItemLayout">
            <a-textarea :rows="6" v-decorator="[
            'content',
             { rules: [{ required: true, message: '请输入评价内容!' }] }
            ]"/>
          </a-form-item>
        </a-col>
        <a-col :span="24">
          <a-form-item label='图册' v-bind="formItemLayout">
            <a-upload
              name="avatar"
              action="http://127.0.0.1:9527/file/fileUpload/"
              list-type="picture-card"
              :file-list="fileList"
              @preview="handlePreview"
              @change="picHandleChange"
            >
              <div v-if="fileList.length < 8">
                <a-icon type="plus" />
                <div class="ant-upload-text">
                  Upload
                </div>
              </div>
            </a-upload>
            <a-modal :visible="previewVisible" :footer="null" @cancel="handleCancel">
              <img alt="example" style="width: 100%" :src="previewImage" />
            </a-modal>
          </a-form-item>
        </a-col>
      </a-row>
    </a-form>
  </a-modal>
</template>

<script>
import {mapState} from 'vuex'
function getBase64 (file) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader()
    reader.readAsDataURL(file)
    reader.onload = () => resolve(reader.result)
    reader.onerror = error => reject(error)
  })
}
const formItemLayout = {
  labelCol: { span: 24 },
  wrapperCol: { span: 24 }
}
export default {
  name: 'orderEvaluate',
  props: {
    orderEvaluateVisiable: {
      default: false
    },
    orderData: {
      type: Object
    }
  },
  computed: {
    ...mapState({
      currentUser: state => state.account.user
    }),
    show: {
      get: function () {
        return this.orderEvaluateVisiable
      },
      set: function () {
      }
    }
  },
  data () {
    return {
      formItemLayout,
      form: this.$form.createForm(this),
      loading: false,
      fileList: [],
      previewVisible: false,
      previewImage: ''
    }
  },
  methods: {
    dataInit (orderCode) {
      this.$get(`/cos/vehicle-info/order/detail/${orderCode}`).then((r) => {
        this.userInfo = r.data.user
        this.vehicleInfo = r.data.vehicle
        this.orderInfo = r.data.order
        this.imagesInit(this.vehicleInfo.images)
      })
    },
    handleCancel () {
      this.previewVisible = false
    },
    async handlePreview (file) {
      if (!file.url && !file.preview) {
        file.preview = await getBase64(file.originFileObj)
      }
      this.previewImage = file.url || file.preview
      this.previewVisible = true
    },
    picHandleChange ({ fileList }) {
      this.fileList = fileList
    },
    reset () {
      this.loading = false
      this.form.resetFields()
    },
    onClose () {
      this.reset()
      this.$emit('close')
    },
    handleSubmit () {
      // 获取图片List
      let images = []
      this.fileList.forEach(image => {
        images.push(image.response)
      })
      this.form.validateFields((err, values) => {
        values.images = images.length > 0 ? images.join(',') : null
        values.orderCode = this.orderData.code
        if (!err) {
          this.loading = true
          this.$post('/cos/order-info/orderEvaluate', {
            ...values
          }).then((r) => {
            this.reset()
            this.$emit('success')
          }).catch(() => {
            this.loading = false
          })
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
