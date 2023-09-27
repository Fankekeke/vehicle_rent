<template>
  <a-modal v-model="show" title="订单处理" @cancel="onClose" :width="1200">
    <template slot="footer">
      <a-button key="back" @click="submit" type="primary">
        分配
      </a-button>
      <a-button @click="onClose">
        关闭
      </a-button>
    </template>
    <div style="font-size: 13px;font-family: SimHei" v-if="orderInfo !== null">
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">订单信息</span></a-col>
        <a-col :span="6"><b>订单编号：</b>
          {{ orderInfo.code }}
        </a-col>
        <a-col :span="6"><b>订单名称：</b>
          {{ orderInfo.orderName ? orderInfo.orderName : '- -' }}
        </a-col>
        <a-col :span="6"><b>车辆每日租金：</b>
          {{ orderInfo.dayPrice ? orderInfo.dayPrice : '- -' }} 元
        </a-col>
        <a-col :span="6"><b>总价格：</b>
          {{ orderInfo.total }} 元
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="6"><b>租车天数：</b>
          {{ orderInfo.rentDay }} 天
        </a-col>
        <a-col :span="6"><b>开始租车时间：</b>
          {{ orderInfo.startDate ? orderInfo.startDate : '- -' }}
        </a-col>
        <a-col :span="6"><b>归还车辆时间</b>
          {{ orderInfo.endDate ? orderInfo.endDate : '- -' }}
        </a-col>
        <a-col :span="6"><b>下单时间：</b>
          {{ orderInfo.createDate }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="6"><b>订单状态：</b>
          <span v-if="orderInfo.status == -1">未支付</span>
          <span v-if="orderInfo.status == 0">未完成</span>
          <span v-if="orderInfo.status == 1">已完成</span>
        </a-col>
        <a-col :span="18"><b>备注：</b>
          {{ orderInfo.remark }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="12">
          <a-form-item label='取车店铺' v-bind="formItemLayout">
            <a-select v-model="taskShop" style="width: 50%">
              <a-select-option :value="item.name" v-for="(item, index) in shopList" :key="index">{{ item.name }}</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
        <a-col :span="12">
          <a-form-item label='还车店铺' v-bind="formItemLayout">
            <a-select v-model="returnShop" style="width: 50%">
              <a-select-option :value="item.name" v-for="(item, index) in shopList" :key="index">{{ item.name }}</a-select-option>
            </a-select>
          </a-form-item>
        </a-col>
      </a-row>
    </div>
    <br/>
    <br/>
    <div style="font-size: 13px;font-family: SimHei" v-if="userInfo !== null">
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">租赁用户</span></a-col>
        <a-col :span="6"><b>用户姓名：</b>
          {{ userInfo.name }}
        </a-col>
        <a-col :span="6"><b>联系电话：</b>
          {{ userInfo.phone ? userInfo.phone : '- -' }}
        </a-col>
        <a-col :span="6"><b>会员编号：</b>
          {{ userInfo.code ? userInfo.code : '- -' }}
        </a-col>
      </a-row>
    </div>
    <br/>
    <br/>
    <div style="font-size: 13px;font-family: SimHei" v-if="vehicleInfo !== null">
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">车辆信息</span></a-col>
        <a-col :span="6"><b>车辆编号：</b>
          {{ vehicleInfo.vehicleNo }}
        </a-col>
        <a-col :span="6"><b>车牌号：</b>
          {{ vehicleInfo.vehicleNumber ? vehicleInfo.vehicleNumber : '- -' }}
        </a-col>
        <a-col :span="6"><b>车辆颜色：</b>
          {{ vehicleInfo.vehicleColor ? vehicleInfo.vehicleColor : '- -' }}
        </a-col>
        <a-col :span="6"><b>车辆名称：</b>
          {{ vehicleInfo.name }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="6"><b>载客数量：</b>
          {{ vehicleInfo.carryPassengers }}
        </a-col>
        <a-col :span="6"><b>发动机号码：</b>
          {{ vehicleInfo.engineNo }}
        </a-col>
        <a-col :span="6"><b>车辆状态：</b>
          <span v-if="vehicleInfo.status == 0" style="color: red">空闲</span>
          <span v-if="vehicleInfo.status == 1" style="color: green">使用中</span>
          <span v-if="vehicleInfo.status == 2" style="color: green">维修中</span>
          <span v-if="vehicleInfo.status == 3" style="color: green">已报废</span>
        </a-col>
        <a-col :span="6"><b>负责人：</b>
          {{ vehicleInfo.principal }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col :span="6"><b>联系电话：</b>
          {{ vehicleInfo.phone }}
        </a-col>
        <a-col :span="6"><b>出厂日期：</b>
          {{ vehicleInfo.factoryDate }}
        </a-col>
        <a-col :span="6"><b>排放标准：</b>
          {{ vehicleInfo.emissionStandard }}
        </a-col>
        <a-col :span="6"><b>燃料类型：</b>
          <span v-if="vehicleInfo.fuelType == 1" style="color: green">燃油</span>
          <span v-if="vehicleInfo.fuelType == 2" style="color: green">柴油</span>
          <span v-if="vehicleInfo.fuelType == 3" style="color: green">油电混动</span>
          <span v-if="vehicleInfo.fuelType == 4" style="color: green">电能</span>
        </a-col>
        <br/>
        <br/>
        <a-col :span="24"><b>备注：</b>
          {{ vehicleInfo.content }}
        </a-col>
      </a-row>
      <br/>
      <a-row style="padding-left: 24px;padding-right: 24px;">
        <a-col style="margin-bottom: 15px"><span style="font-size: 15px;font-weight: 650;color: #000c17">图册</span></a-col>
        <a-col :span="24">
          <a-upload
            name="avatar"
            action="http://127.0.0.1:9527/file/fileUpload/"
            list-type="picture-card"
            :file-list="fileList"
            @preview="handlePreview"
            @change="picHandleChange"
          >
          </a-upload>
          <a-modal :visible="previewVisible" :footer="null" @cancel="handleCancel">
            <img alt="example" style="width: 100%" :src="previewImage" />
          </a-modal>
        </a-col>
      </a-row>
      <br/>
    </div>
  </a-modal>
</template>

<script>
import moment from 'moment'
import {mapState} from 'vuex'
function getBase64 (file) {
  return new Promise((resolve, reject) => {
    const reader = new FileReader()
    reader.readAsDataURL(file)
    reader.onload = () => resolve(reader.result)
    reader.onerror = error => reject(error)
  })
}
moment.locale('zh-cn')
const formItemLayout = {
  labelCol: { span: 24 },
  wrapperCol: { span: 24 }
}
export default {
  name: 'OrderAudit',
  props: {
    orderShow: {
      type: Boolean,
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
        return this.orderShow
      },
      set: function () {
      }
    }
  },
  watch: {
    orderShow: function (value) {
      if (value) {
        this.dataInit(this.orderData.id)
      }
    }
  },
  data () {
    return {
      formItemLayout,
      loading: false,
      fileList: [],
      previewVisible: false,
      previewImage: '',
      repairInfo: null,
      reserveInfo: null,
      durgList: [],
      logisticsList: [],
      current: 0,
      userInfo: null,
      orderInfo: null,
      vehicleInfo: null,
      shopList: [],
      taskShop: '',
      returnShop: ''
    }
  },
  mounted () {
    this.selectShopList()
  },
  methods: {
    selectShopList () {
      this.$get(`/cos/shop-info/datalist`).then((r) => {
        this.shopList = r.data.data
      })
    },
    moment,
    dataInit (orderId) {
      this.$get(`/cos/vehicle-info/order/detail/${orderId}`).then((r) => {
        this.userInfo = r.data.user
        this.orderInfo = r.data.order
        this.vehicleInfo = r.data.vehicle
        this.imagesInit(this.orderData.images)
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
    imagesInit (images) {
      if (images !== null && images !== '') {
        let imageList = []
        images.split(',').forEach((image, index) => {
          imageList.push({uid: index, name: image, status: 'done', url: 'http://127.0.0.1:9527/imagesWeb/' + image})
        })
        this.fileList = imageList
      }
    },
    submit () {
      if (this.driverCheck.length !== 0) {
        this.$get(`/cos/order-info/checkOrder`, {
          'orderCode': this.orderData.code,
          'driverCode': this.driverCheck,
          'staffCodeStr': this.staffCheck.join(',')
        }).then((r) => {
          this.cleanData()
          this.$emit('success')
        })
      } else {
        this.$message.warn('请选择司机')
      }
    },
    onClose () {
      this.cleanData()
      this.$emit('close')
    },
    cleanData () {
      this.staffCheck = []
      this.driverCheck = []
    }
  }
}
</script>

<style scoped>

</style>
