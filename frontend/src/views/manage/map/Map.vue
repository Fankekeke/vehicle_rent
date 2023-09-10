<template>
  <a-drawer
    placement="right"
    width="100%"
    :closable="false"
    :visible="orderShow"
    destroyOnClose
    wrapClassName="aa"
    :getContainer="false"
  >
    <div style="width: 100%">
      <a-icon type="arrow-left" style="position: absolute;z-index: 999;color: red;font-size: 20px;margin: 15px" @click="home"/>
      <a-row style="height:100vh;font-family: SimHei">
        <a-col :span="17" style="height: 100%;">
          <div id="areas" style="width: 100%;height: 100%;box-shadow: 3px 3px 3px rgba(0, 0, 0, .2);background:#ec9e3c;color:#fff"></div>
        </a-col>
        <a-col :span="7" style="height: 100%;box-shadow: 3px 3px 3px rgba(0, 0, 0, .2);color:#fff">
          <div>
            <div class="scenicInfo" style="height: 100vh; overflow-y: auto;padding-left: 5px;overflow-x: hidden">
              <a-carousel autoplay style="height: 300px;" v-if="orderData.images !== undefined && orderData.images !== ''">
                <div style="width: 100%;height: 300px" v-for="(item, index) in orderData.images.split(',')" :key="index">
                  <img :src="'http://127.0.0.1:9527/imagesWeb/'+item" style="width: 100%;height: 100%">
                </div>
              </a-carousel>
              <a-card :title="orderData.startDate +' ~ '+ orderData.endDate" :bordered="false">
              </a-card>
              <div style="font-size: 12px;font-family: SimHei;color: #404040;margin-top: 15px">
                <a-row style="padding-left: 24px;padding-right: 24px;">
                  <a-col :span="24"><b>订单编号：</b>
                    {{ orderData.code !== null ? orderData.code : '- -' }}
                  </a-col>
                </a-row>
                <br/>
                <a-row style="padding-left: 24px;padding-right: 24px;" v-if="userInfo != null">
                  <a-col style="margin-bottom: 15px"><span style="font-size: 14px;font-weight: 650;color: #000c17">客户信息</span></a-col>
                  <a-col :span="8"><b>客户编号：</b>
                    {{ userInfo.code !== null ? userInfo.code : '- -' }}
                  </a-col>
                  <a-col :span="8"><b>客户名称：</b>
                    {{ userInfo.name !== null ? userInfo.name : '- -' }}
                  </a-col>
                  <a-col :span="8"><b>联系方式：</b>
                    {{ userInfo.phone !== null ? userInfo.phone : '- -' }}
                  </a-col>
                </a-row>
                <br/>
                <br/>
                <a-row style="padding-left: 24px;padding-right: 24px;">
                  <a-col style="margin-bottom: 15px"><span style="font-size: 14px;font-weight: 650;color: #000c17">订单信息</span></a-col>
                  <a-col :span="8"><b>订单名称：</b>
                    {{ orderData.orderName ? orderData.orderName : '- -' }}
                  </a-col>
                  <a-col :span="8"><b>车辆每日租金：</b>
                    {{ orderData.dayPrice ? orderData.dayPrice : '- -' }} 元
                  </a-col>
                  <a-col :span="8"><b>总价格：</b>
                    {{ orderData.total }} 元
                  </a-col>
                </a-row>
                <br/>
                <a-row style="padding-left: 24px;padding-right: 24px;">
                  <a-col :span="8"><b>租车天数：</b>
                    {{ orderData.rentDay }} 天
                  </a-col>
                  <a-col :span="8"><b>下单时间：</b>
                    {{ orderData.createDate }}
                  </a-col>
                  <a-col :span="8"><b>订单状态：</b>
                    <span v-if="orderData.status == -1">未支付</span>
                    <span v-if="orderData.status == 0">未完成</span>
                    <span v-if="orderData.status == 1">已完成</span>
                  </a-col>
                </a-row>
                <br/>
                <a-row style="padding-left: 24px;padding-right: 24px;">
                  <a-col :span="24"><b>取车店铺：</b>
                    {{ orderData.takeShop ? orderData.takeShop : '- -' }}
                  </a-col>
                  <br/>
                  <br/>
                  <a-col :span="24"><b>归还车辆店铺：</b>
                    {{ orderData.returnShop ? orderData.returnShop : '- -' }}
                  </a-col>
                  <br/>
                  <br/>
                  <a-col :span="24"><b>备注：</b>
                    {{ orderData.remark ? orderData.remark : '- -' }}
                  </a-col>
                </a-row>
                <br/>
                <br/>
                <a-row style="padding-left: 24px;padding-right: 24px;" v-if="vehicleInfo != null">
                  <a-col style="margin-bottom: 15px"><span style="font-size: 14px;font-weight: 650;color: #000c17">车辆信息</span></a-col>
                  <a-col :span="8"><b>车辆编号：</b>
                    {{ vehicleInfo.vehicleNo }}
                  </a-col>
                  <a-col :span="8"><b>车牌号：</b>
                    {{ vehicleInfo.vehicleNumber ? vehicleInfo.vehicleNumber : '- -' }}
                  </a-col>
                  <a-col :span="8"><b>车辆颜色：</b>
                    {{ vehicleInfo.vehicleColor ? vehicleInfo.vehicleColor : '- -' }}
                  </a-col>
                </a-row>
                <br/>
                <a-row style="padding-left: 24px;padding-right: 24px;" v-if="vehicleInfo != null">
                  <a-col :span="8"><b>车辆名称：</b>
                    {{ vehicleInfo.name }}
                  </a-col>
                  <a-col :span="8"><b>载客数量：</b>
                    {{ vehicleInfo.carryPassengers }}
                  </a-col>
                  <a-col :span="8"><b>发动机号码：</b>
                    {{ vehicleInfo.engineNo }}
                  </a-col>
                </a-row>
                <br/>
                <a-row style="padding-left: 24px;padding-right: 24px;" v-if="vehicleInfo != null">
                  <a-col :span="8"><b>车辆状态：</b>
                    <span v-if="vehicleInfo.status == 0" style="color: red">空闲</span>
                    <span v-if="vehicleInfo.status == 1" style="color: green">使用中</span>
                    <span v-if="vehicleInfo.status == 2" style="color: green">维修中</span>
                    <span v-if="vehicleInfo.status == 3" style="color: green">已报废</span>
                  </a-col>
                  <a-col :span="8"><b>负责人：</b>
                    {{ vehicleInfo.principal }}
                  </a-col>
                  <a-col :span="8"><b>联系电话：</b>
                    {{ vehicleInfo.phone }}
                  </a-col>
                </a-row>
                <br/>
                <a-row style="padding-left: 24px;padding-right: 24px;" v-if="vehicleInfo != null">
                  <a-col :span="8"><b>出厂日期：</b>
                    {{ vehicleInfo.factoryDate }}
                  </a-col>
                  <a-col :span="8"><b>排放标准：</b>
                    {{ vehicleInfo.emissionStandard }}
                  </a-col>
                  <a-col :span="8"><b>燃料类型：</b>
                    <span v-if="vehicleInfo.fuelType == 1" style="color: green">燃油</span>
                    <span v-if="vehicleInfo.fuelType == 2" style="color: green">柴油</span>
                    <span v-if="vehicleInfo.fuelType == 3" style="color: green">油电混动</span>
                    <span v-if="vehicleInfo.fuelType == 4" style="color: green">电能</span>
                  </a-col>
                </a-row>
                <br/>
                <br/>
                <a-row style="padding-left: 24px;padding-right: 24px;">
                  <a-col style="margin-bottom: 15px"><span style="font-size: 14px;font-weight: 650;color: #000c17">地图标点</span></a-col>
                  <a-col :span="24">
                    <a-radio-group button-style="solid" style="width: 100%" @change="gisOnChange">
                      <a-radio-button value="1" style="width: 50%;text-align: center">
                        取车地址
                      </a-radio-button>
                      <a-radio-button value="2" style="width: 50%;text-align: center">
                        还车地址
                      </a-radio-button>
                    </a-radio-group>
                  </a-col>
                </a-row>
                <br/>
                <br/>
                <a-row style="padding-left: 24px;padding-right: 24px;" v-if="evaluateInfo != null">
                  <a-col><span style="font-size: 14px;font-weight: 650;color: #000c17">订单评价</span></a-col>
                </a-row>
                <a-card v-if="evaluateInfo != null" hoverable :bordered="false" style="width: 100%">
                  <a-skeleton active v-if="checkLoading" />
                  <apexchart v-if="!checkLoading" type="radar" height="300" :options="chartOptions" :series="series"></apexchart>
                </a-card>
                <br/>
                <br/>
              </div>
            </div>
          </div>
        </a-col>
      </a-row>
    </div>
  </a-drawer>
</template>

<script>
import baiduMap from '@/utils/map/baiduMap'
export default {
  name: 'Map',
  props: {
    orderShow: {
      type: Boolean,
      default: false
    },
    orderData: {
      type: Object
    }
  },
  data () {
    return {
      staffList: [],
      evaluateInfo: null,
      vehicleInfo: null,
      userInfo: null,
      communityRent: 0,
      rentShow: false,
      communityShow: false,
      communityData: null,
      checkedList: ['Apple', 'Orange'],
      indeterminate: true,
      checkAll: false,
      plainOptions: ['Apple', 'Pear', 'Orange'],
      visible: false,
      rentList: [],
      communityList: [],
      community: null,
      nowPoint: null,
      roadData: [],
      checkLoading: false,
      echartsShow: false,
      getShop: null,
      putShop: null,
      series: [{
        name: '得分',
        data: []
      }],
      chartOptions: {
        chart: {
          height: 380,
          type: 'radar'
        },
        title: {
          text: ''
        },
        xaxis: {
          categories: ['交付得分', '价格得分', '质量得分', '准时得分', '服务得分', '综合得分']
        }
      }
    }
  },
  watch: {
    'orderShow': function (value) {
      if (value) {
        this.dataInit(this.orderData.id)
        setTimeout(() => {
          baiduMap.initMap('areas')
          this.getLocal()
        }, 200)
      }
    }
  },
  methods: {
    dataInit (orderId) {
      this.checkLoading = true
      this.$get(`/cos/vehicle-info/order/detail/${orderId}`).then((r) => {
        this.userInfo = r.data.user
        this.orderInfo = r.data.order
        this.vehicleInfo = r.data.vehicle
        this.getShop = r.data.getShop
        this.putShop = r.data.putShop
        this.checkLoading = false
      })
    },
    navigation (data) {
      baiduMap.clearOverlays()
      baiduMap.rMap().enableScrollWheelZoom(true)
      // eslint-disable-next-line no-undef
      let driving = new BMap.DrivingRoute(baiduMap.rMap(), {renderOptions: {map: baiduMap.rMap(), autoViewport: true}})
      // eslint-disable-next-line no-undef
      driving.search(new BMap.Point(data.startLongitude, data.startLatitude), new BMap.Point(data.endLongitude, data.endLatitude))
      // this.getRoadData()
    },
    getRoadData () {
      let options = {
        onSearchComplete: results => {
          // eslint-disable-next-line eqeqeq,no-undef
          if (driving.getStatus() == BMAP_STATUS_SUCCESS) {
            // 获取第一条方案
            let plan = results.getPlan(0)
            // 获取方案的驾车线路
            // eslint-disable-next-line no-unused-vars
            let route = plan.getRoute(0)
            // 获取每个关键步骤,并输出到页面
            let s = []
            for (let j = 0; j < plan.getNumRoutes(); j++) {
              let route = plan.getRoute(j)
              for (let i = 0; i < route.getNumSteps(); i++) {
                let step = route.getStep(i)
                s.push((i + 1) + '. ' + step.getDescription())
              }
            }
            this.roadData = s
          }
        }
      }
      // eslint-disable-next-line no-undef
      let driving = new BMap.DrivingRoute(baiduMap.rMap(), options)
      // eslint-disable-next-line no-undef
      let start = new BMap.Point(this.nowPoint.lng, this.nowPoint.lat)
      let end = null
      if (this.rentShow) {
        end = new BMap.Point(this.rentData.longitude, this.rentData.latitude)
      } else {
        end = new BMap.Point(this.communityData.longitude, this.communityData.latitude)
      }
      // eslint-disable-next-line no-undef
      driving.search(start, end)
    },
    checkEvaluate (score) {
      let data = [score.deliverScore, score.priceScore, score.qualityScore, score.scheduleScore, score.serviceScore, score.overScore, 100]
      this.series[0].data = data
    },
    home () {
      this.$emit('close')
    },
    gisOnChange (e) {
      let data = null
      switch (e.target.value) {
        case '1':
          data = this.getShop
          break
        case '2':
          data = this.putShop
          break
      }
      baiduMap.rMap().enableScrollWheelZoom(true)
      // eslint-disable-next-line no-undef
      let driving = new BMap.DrivingRoute(baiduMap.rMap(), {renderOptions: {map: baiduMap.rMap(), autoViewport: true}})
      // eslint-disable-next-line no-undef
      driving.search(new BMap.Point(this.nowPoint.lng, this.nowPoint.lat), new BMap.Point(data.longitude, data.latitude))
    },
    getLocal () {
      // eslint-disable-next-line no-undef
      let geolocation = new BMap.Geolocation()
      geolocation.getCurrentPosition(r => {
        this.nowPoint = r.point
      }, {enableHighAccuracy: true})
    }
  }
}
</script>

<style scoped>
  >>> .ant-drawer-body {
    padding: 0 !important;
  }
  >>> .ant-card-meta-title {
    font-size: 13px;
    font-family: SimHei;
  }
  >>> .ant-card-meta-description {
    font-size: 12px;
    font-family: SimHei;
  }
  >>> .ant-divider-with-text-left {
    margin: 0;
  }

  >>> .ant-card-head-title {
    font-size: 13px;
    font-family: SimHei;
  }
  >>> .ant-card-extra {
    font-size: 13px;
    font-family: SimHei;
  }
  >>> .ant-radio-button-wrapper {
    border-radius: 0;
  }
</style>
